package br.com.fiap.isgood.model
import android.app.Application
import br.com.fiap.isgood.model.dao.LoginDAO
import br.com.fiap.isgood.model.dao.UsuarioDAO
import org.junit.Test
import java.lang.Exception


class UsuarioTest {

    lateinit var authUser : Usuario

    @Test
    fun `Should return true if user Willian could authenticate`(){
        authUser = UsuarioDAO.getByEmail("williandrade@gmail.com")
        LoginDAO.signInWithEmailAndPassword(Credential("williandrade@gmail.com", "teste123"),Application().applicationContext, remember = false)
        LoginDAO.autenticado.observeForever {
            assert(true)
        }
        LoginDAO.autenticando.observeForever {
            if (it == false)
                assert(false)
        }
    }

    @Test
    fun `Should return true if user Chatonildo could authenticate`(){
        try {
            authUser = UsuarioDAO.getByEmail("outro@email.com")
        } catch (e : Exception){
            assert(false)
        }
        assert (authUser.name.equals("Chatonildo da Silva"))
    }

}