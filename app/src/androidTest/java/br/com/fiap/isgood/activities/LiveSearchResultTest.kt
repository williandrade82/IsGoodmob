package br.com.fiap.isgood.activities

import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import br.com.fiap.isgood.R
import br.com.fiap.isgood.model.Restaurante
import br.com.fiap.isgood.model.dao.RestauranteDAO
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LiveSearchResultTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun liveSearchResultTest(){
        pausedLog("Aguardando 1s pra iniciar a aplicação")

        pausedLog("Fazendo o login", 500)

        onView(withId(R.id.editTextEmail))
            .perform(replaceText("williandrade@gmail.com"))

        pausedLog("Preenchendo a senha", 500)
        onView(withId(R.id.editTextPassword))
            .perform(replaceText("teste123"))

        pausedLog("Confirmando o login", 500)
        onView(withId(R.id.btLogin)).perform(click())

        pausedLog("Aguardando 5s para confirmar o login e mostrar a tela de pesquisa", 5000)

        pausedLog("Preenchendo o campo de pesquisa", 500)
        onView(withId(R.id.searchBar)).perform( click())

        pausedLog("Digitando agora", 500)
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform( replaceText ("afon"))

        pausedLog("Aguarda execução da pesquisa para confirmar")

        onView(withId(R.id.textViewNomeRestaurante))
            .check(matches(withText(RestauranteDAO.getSampleData()[2].nome)))

    }

    fun pausedLog(message : String, timeSleep : Long = 1000) {
        Log.println (Log.DEBUG, "TEST", "$message | Aguardando ${timeSleep}ms")
        Thread.sleep(timeSleep)
    }
}