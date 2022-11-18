package br.com.fiap.isgood.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.fiap.isgood.messageTool.mobcomponents.CustomToast
import br.com.fiap.isgood.model.Credential
import br.com.fiap.isgood.model.dao.LoginDAO

class CadastroActivityViewModel() : ViewModel() {
    val nome = MutableLiveData("")
    val email = MutableLiveData("")
    val senha = MutableLiveData("")
    val confirmaSenha = MutableLiveData("")
    val cep = MutableLiveData("")
    var kindMensagem = CustomToast.KIND_DEFAULT
    val mensagem = MutableLiveData("")
    val tudoPreenchido = MutableLiveData(false)
    val senhasConferem = MutableLiveData(true)

    init {
        LoginDAO.statusMessage.value = ""

        nome.observeForever {
            podeCadastrar()
        }

        email.observeForever {
            podeCadastrar()
        }

        senha.observeForever {
            podeCadastrar()
        }
        confirmaSenha.observeForever {
            podeCadastrar()
        }
        cep.observeForever {
            podeCadastrar()
        }

        LoginDAO.statusMessage.observeForever {
            kindMensagem = LoginDAO.statusMessageKind.value ?: CustomToast.KIND_DEFAULT
            mensagem.value = LoginDAO.statusMessage.value
            podeCadastrar()
        }

    }

    private fun podeCadastrar(): Boolean {
        //Todos os campos com valor
        tudoPreenchido.value = !(
                email.value.toString() == ""
                        || nome.value.toString() == ""
                        || email.value.toString() == ""
                        || nome.value.toString() == ""
                        || senha.value.toString() == ""
                        || confirmaSenha.value.toString() == ""
                        || cep.value.toString() == ""
                        || LoginDAO.criandoUsuario.value==true)

        // Se tiver todos os dados registrados
        senhasConferem.value = senha.value.toString() == confirmaSenha.value.toString()

        return (tudoPreenchido.value == true && senhasConferem.value == true)
    }

    fun setMessage(message : String, kind : Int = CustomToast.KIND_DEFAULT){
        this.kindMensagem = kind
        mensagem.value = message
    }

    fun doCadastro(username : String, usercep : String, context: Context) {

        setMessage("Criando seu registro...", CustomToast.KIND_INFORMATION)

        if (tudoPreenchido.value == false) {
            setMessage("Preencha todos os dados.", CustomToast.KIND_ERROR)
            return
        }
        if (senhasConferem.value == false) {
            setMessage("Senhas não conferem.", CustomToast.KIND_WARNING)
            return
        }

        if (LoginDAO.criandoUsuario.value==true) {
            setMessage("Ainda criando o seu registro, aguarde mais um pouco.", CustomToast.KIND_INFORMATION)
            return
        }

        // Condições favoráveis para fazer o cadastro
        // Cria usuário no Firebase
        LoginDAO.newUser(Credential(email.value.toString(), senha.value.toString()), username = username, usercep = usercep, context)
        LoginDAO.criandoUsuario.observeForever{
            podeCadastrar()
        }
        podeCadastrar()

    }

}