package br.com.fiap.isgood.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.isgood.databinding.ActivityCadastroBinding
import br.com.fiap.isgood.messageTool.mobcomponents.CustomToast
import br.com.fiap.isgood.model.dao.LoginDAO
import br.com.fiap.isgood.viewmodel.CadastroActivityViewModel

class CadastroActivity : AppCompatActivity() {

    lateinit var binding : ActivityCadastroBinding
    lateinit var cadastroModelView: CadastroActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Associa a acvityViewModel
        cadastroModelView = ViewModelProvider
            .NewInstanceFactory()
            .create(CadastroActivityViewModel::class.java)

        //Para facilitar testes:
        binding.tvEndereceoDeEmail.setOnClickListener {
            binding.edNome.setText("Willian José de Andrade")
            binding.edEmail.setText("williandrade@gmail.com")
            binding.edSenha.setText("teste123")
            binding.edSenhaConfirma.setText("teste123")
            binding.edCep.setText("88034460")
            cadastroModelView.tudoPreenchido.value = true

        }

        // Liga os Listerners
        binding.edEmail.doOnTextChanged { _,_,_,_->
              cadastroModelView.email.value = binding.edEmail.text.toString()
        }

        binding.edNome.doOnTextChanged { _,_,_,_->
            cadastroModelView.nome.value = binding.edNome.text.toString()
        }

        binding.edSenha.doOnTextChanged { _,_,_,_->
            cadastroModelView.senha.value = binding.edSenha.text.toString()
        }

        binding.edSenhaConfirma.doOnTextChanged { _,_,_,_->
            cadastroModelView.confirmaSenha.value = binding.edSenhaConfirma.text.toString()
        }

        binding.edCep.doOnTextChanged { _,_,_,_->
            cadastroModelView.cep.value = binding.edCep.text.toString()
        }

        binding.btCadastro.setOnClickListener{
            cadastroModelView.doCadastro(binding.edNome.text.toString(), binding.edCep.text.toString(), applicationContext)
        }

        //Reações com os watchers
        cadastroModelView.senhasConferem.observe(this){
            binding.tvSenhaConfirma.setBackgroundColor(if (it) Color.WHITE else Color.MAGENTA)
        }

        LoginDAO.autenticado.observe(this){
            if (it) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                this.finish()
            }
        }

        cadastroModelView.tudoPreenchido.observe(this){
            binding.btCadastro.isEnabled = it
        }

        cadastroModelView.mensagem.observe(this){
            showMessage(it, cadastroModelView.kindMensagem)
        }

    }

    fun showMessage (mensagem:String, kind : Int) {
        if (mensagem.isEmpty()) return
        CustomToast.showByKind(this, mensagem, kind)
    }
}