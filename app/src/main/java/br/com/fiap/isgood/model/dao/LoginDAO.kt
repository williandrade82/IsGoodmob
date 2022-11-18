package br.com.fiap.isgood.model.dao

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import br.com.fiap.isgood.messageTool.mobcomponents.CustomToast
import br.com.fiap.isgood.model.Credential
import br.com.fiap.isgood.model.Usuario
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object LoginDAO {

    var auth = Firebase.auth
    private val usuario = MutableLiveData<Usuario>()

    //Mensage para o usuário
    val statusMessage = MutableLiveData("")
    val statusMessageKind = MutableLiveData(CustomToast.KIND_DEFAULT)


    //Controles de status
    var criandoUsuario = MutableLiveData(false)
    var autenticado = MutableLiveData(false)
    var autenticando = MutableLiveData(false)

    // Valores fixos
    val SHARED_PREFERENCES_LIB = this.javaClass.toString()
    val PREFERENCES_KEY_EMAIL_LOGIN = "key_email"
    val PREFERENCES_KEY_PASSWORD_LOGIN = "key_password"

    // Funções auxiliares
    private fun setMensagem(mensagem : String, kind : Int){
        statusMessageKind.value = kind
        statusMessage.value = mensagem
    }

    fun getSharedPreferencesEditor(context: Context): SharedPreferences.Editor {
        return context.getSharedPreferences(SHARED_PREFERENCES_LIB, Context.MODE_PRIVATE).edit()
    }

    private fun saveOnPreferences(
        context: Context,
        keyEmail: String,
        keyPassword: String
    ) {
        getSharedPreferencesEditor(context = context)
            .putString(PREFERENCES_KEY_EMAIL_LOGIN, keyEmail)
            .putString(PREFERENCES_KEY_PASSWORD_LOGIN, keyPassword)
            .apply()
        Log.i(
            "LoginDAO.saveOnPreferences", "Informações salvas localmente: " +
                    "$PREFERENCES_KEY_EMAIL_LOGIN: ${keyEmail} | " +
                    "$PREFERENCES_KEY_PASSWORD_LOGIN: ${keyPassword.hashCode()}"
        )
    }

    fun setUserAttribs(credential: Credential? = null, name : String? = null, cep : String? = null, ){

        if (usuario.value == null) {
            if (credential==null) Exception("Credencial não definida.")
            usuario.value = Usuario(auth.uid!!, name!!, credential!!.email, cep!!)
        } else {
            if (name != null) usuario.value?.name = name
            if (cep != null) usuario.value?.cep = cep
            if (credential != null)
                if (credential.email != null) usuario.value?.email = credential.email.toString()
        }
    }

    fun newUser(credential: Credential, username: String, usercep : String, context: Context) {
        criandoUsuario.value = true

        auth.createUserWithEmailAndPassword(credential.email, credential.password)
            .addOnSuccessListener {
                setUserAttribs(credential = credential, name = username, cep=usercep)
                setAuthenticatedUser(credential, context, false)
            }
            .addOnFailureListener {
                if (it.message.equals("The email address is already in use by another account."))
                    setMensagem("Esse e-mail já está registrado para outro usuário.", CustomToast.KIND_WARNING)
                else if (it.message.toString().contains("The email address is badly formatted"))
                    setMensagem("O endreço de e-mail está mal formatado.", CustomToast.KIND_ERROR)
                else if (it.message.toString().contains("The given password is invalid. [ Password should be at least 6 characters ]"))
                    setMensagem("A senha pecisa ter pelo menos 6 caracteres", CustomToast.KIND_WARNING)
                else
                    setMensagem("Falha na criação de usuário. Verifique o log para maiores detalhes.", CustomToast.KIND_WARNING)
                Log.e("LoginDAO.newUser", "Message: ${it.message}")
                criandoUsuario.value = false
            }.addOnCanceledListener {
                setMensagem("Criação de usuário cancelada.", CustomToast.KIND_INFORMATION)
                criandoUsuario.value = false
            }
    }

    private fun setAuthenticatedUser(credential: Credential, context: Context, remember: Boolean) {
        //Salva os dados do usuário no celular, pra facilitar um próximo acesso.
        if (remember)
            saveOnPreferences(context, credential.email, credential.password)

        try {
            usuario.value = UsuarioDAO.getByEmail(credential.email)
        } catch (e: Exception) {
            usuario.value = UsuarioDAO.add(Usuario("", "", credential.email, ""))
        }

        setMensagem("Usuario autenticado.\nSeja bem vindo ${usuario.value?.name}", CustomToast.KIND_SUCCESS)

        autenticando.value = false
        criandoUsuario.value = false
        autenticado.value = true

    }

    fun signInWithEmailAndPassword(credential: Credential, context: Context, remember : Boolean) {
        autenticando.value = true
        auth.signInWithEmailAndPassword(credential.email, credential.password)
            .addOnSuccessListener {
                setAuthenticatedUser(credential, context, remember)

            }
            .addOnFailureListener {
                setMessageOnFailureLogin(it)
            }
            .addOnCanceledListener {
                setMensagem("Autenticação cancelada.", CustomToast.KIND_INFORMATION)
                autenticando.value = false
            }
    }

    fun signInWithSharedData(context: Context) {
        // Verificar se já existe algum token cadastrado

        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_LIB, Context.MODE_PRIVATE)
        val email = sharedPref.getString(PREFERENCES_KEY_EMAIL_LOGIN, "")
        val password = sharedPref.getString(PREFERENCES_KEY_PASSWORD_LOGIN, "")

        if (email.toString().isNotEmpty() && password.toString().isNotEmpty()) {
            //Faz login pelo usuário e senha gravados
            setMensagem("Lembrei de você!\nAutenticando com usuário salvo.", CustomToast.KIND_INFORMATION)
            signInWithEmailAndPassword(Credential(email.toString(), password.toString()), context, false)
        } else {
            Log.i(
                "LoginDAO.tryLoginWithSharedData",
                "Não foram encontrados registros de e-mail e senha registrados localmente para login automático."
            )
        }
    }

    private fun setMessageOnFailureLogin(it: Exception) {
        if (it.message!!.startsWith("We have blocked all requests from this device due to unusual activity. Try again")) {
            setMensagem("Usuário bloqueado devido a várias tentativas sem sucesso. Tente novamente mais tarde.", CustomToast.KIND_WARNING)
        } else if (it.message!!.startsWith("The password is invalid or the user does not have a password")) {
            setMensagem("Senha inválida.", CustomToast.KIND_ERROR)
        } else if (it.message!!.startsWith("There is no user record corresponding to this identifier.")) {
            setMensagem("Não existe usuário registrado com esse e-mail.", CustomToast.KIND_WARNING)
        } else {
            setMensagem("Falha ao autenticar o usuário.\n${it.message}.", CustomToast.KIND_ERROR)
        }
        autenticando.value = false
    }

    fun signOut(context: Context){
        if (autenticado.value == false)
            return
        auth.signOut()
        saveOnPreferences(context, "", "")
        setMensagem("Volte sempre!", CustomToast.KIND_DEFAULT)
        autenticado.value = false
        usuario.value = null
    }

}