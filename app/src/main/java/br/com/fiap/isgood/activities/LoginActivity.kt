package br.com.fiap.isgood.activities

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import br.com.fiap.isgood.apis.IsGoodApis
import br.com.fiap.isgood.config.RetrofitInstaceFactory
import br.com.fiap.isgood.databinding.ActivityLoginBinding
import br.com.fiap.isgood.messageTool.mobcomponents.CustomToast
import br.com.fiap.isgood.model.dao.LoginDAO
import br.com.fiap.isgood.model.dto.EmpresasDTO
import br.com.fiap.isgood.viewmodel.LoginActivityViewModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var loginViewModel: LoginActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        getEmpresas();
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Ativa o ActivityViewModel
        loginViewModel = ViewModelProvider.NewInstanceFactory().create(LoginActivityViewModel::class.java)

        //Ativando os listeners
        binding.editTextEmail.doOnTextChanged { _, _, _, _ ->
            loginViewModel.email.value = binding.editTextEmail.text.toString()
        }

        binding.editTextPassword.doOnTextChanged{_,_,_,_ ->
            loginViewModel.password.value = binding.editTextPassword.text.toString()
        }

        binding.tvEsqueciMinhaSenha.setOnClickListener {
            CustomToast.info(this,"Funcionalidade ainda não disponível.")
        }

        binding.textViewLoginCriarConta.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }

        binding.btLogin.setOnClickListener{
            loginViewModel.doLogin(context = applicationContext, binding.cbLembrar.isChecked())
        }

        //Ativando os watchers
        loginViewModel.dadosProntos.observe(this){
            binding.btLogin.isEnabled = it
            binding.cbLembrar.isEnabled = it
        }

        LoginDAO.autenticando.observe(this){
            var status = !it
            if (LoginDAO.autenticado.value==true)
                status = false
            binding.textViewLoginCriarConta.isEnabled = status
            binding.tvEsqueciMinhaSenha.isEnabled = status
        }

        LoginDAO.autenticado.observe(this){
            if (it) {
                val intent = Intent(this, PesquisaActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        loginViewModel.mensagem.observe(this){
            System.out.println("Mostrando a mensagem: " + loginViewModel.kindMensagem)
            showMessage(it, loginViewModel.kindMensagem)
        }

        loginViewModel.tryLoginWithSharedData(this.application)

        /*
         * Decorações de tela
         */
        setUnderlinedString(binding.textViewLoginCriarConta)
        setUnderlinedString(binding.tvEsqueciMinhaSenha)

        /*
         * Apenas para facilitar testes no desenvolvimento!!!
         */
        binding.tvEmail.setOnClickListener{
            binding.editTextEmail.setText("williandrade@gmail.com")
            binding.editTextPassword.setText("teste123")
        }

    }
    fun setUnderlinedString(textView : TextView){
        val underlineString = SpannableString(textView.text);
        underlineString.setSpan(UnderlineSpan(), 0, underlineString.length, 0);
        textView.text = underlineString;
    }

    fun showMessage (mensagem: String, kind: Int = 0) {
        System.out.println("showMensagem: '%s'".format(mensagem))
        if (mensagem == "") return

        CustomToast.showByKind(this, mensagem, kind)

    }

    fun getEmpresas(){
        val retrofitClient = RetrofitInstaceFactory.getRetrofitInstance("http://10.0.2.2:8080/")
        val apis = retrofitClient.create(IsGoodApis::class.java)

        apis.getEmpresas().enqueue(object : retrofit2.Callback<List<EmpresasDTO>> {
            override fun onResponse(call: Call<List<EmpresasDTO>>, response: Response<List<EmpresasDTO>>) {
                val responseString: String? = response.body().toString();
                val empresaDTOlist: List<EmpresasDTO>? = response.body();
                println(responseString);
            }

            override fun onFailure(call: Call<List<EmpresasDTO>>, t: Throwable) {
                println("Não foi")
            }

        })
    }

}