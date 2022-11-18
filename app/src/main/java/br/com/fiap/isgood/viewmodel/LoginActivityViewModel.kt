package br.com.fiap.isgood.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.fiap.isgood.messageTool.mobcomponents.CustomToast
import br.com.fiap.isgood.model.Credential
import br.com.fiap.isgood.model.dao.LoginDAO

class LoginActivityViewModel() : ViewModel() {
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val dadosProntos = MutableLiveData(false)
    var kindMensagem = CustomToast.KIND_DEFAULT
    val mensagem = MutableLiveData("")

    init {
        LoginDAO.statusMessage.value = ""

        email.observeForever{
            validaDados()
        }
        password.observeForever{
            validaDados()
        }

        LoginDAO.statusMessage.observeForever {
            kindMensagem = LoginDAO.statusMessageKind.value ?: CustomToast.KIND_DEFAULT
            mensagem.value = LoginDAO.statusMessage.value
            validaDados()
        }

        LoginDAO.autenticando.observeForever {
            validaDados()
        }
    }

    fun tryLoginWithSharedData(context : Context) {
        LoginDAO.signInWithSharedData(context)
    }


    private fun validaDados(){
        val newValue =  !(email.value.toString() == "" || password.value.toString() == ""
                || LoginDAO.autenticando.value == true || LoginDAO.criandoUsuario.value==true)
        if (newValue != dadosProntos.value) dadosProntos.value = newValue
    }

    fun doLogin(context:Context, remember : Boolean = false) {

        if (dadosProntos.value == false) {
            kindMensagem = CustomToast.KIND_INFORMATION
            mensagem.value = "Informe o e-mail para login e a senha."
            return
        }

        if (LoginDAO.autenticando.value==true) {
            kindMensagem = CustomToast.KIND_INFORMATION
            mensagem.value = "Autenticando. Aguarde..."
            return
        }

        kindMensagem = CustomToast.KIND_INFORMATION
        mensagem.value = "Autenticando. Aguarde..."
        LoginDAO.signInWithEmailAndPassword(Credential(email.value.toString(), password.value.toString()), context, remember)
        validaDados()
    }

}