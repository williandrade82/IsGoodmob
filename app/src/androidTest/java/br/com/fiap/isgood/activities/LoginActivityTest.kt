package br.com.fiap.isgood.activities


import android.util.Log
import android.util.Log.i
import android.util.LogPrinter
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import br.com.fiap.isgood.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun loginActivityTest() {

        pausedLog("Aguardando 1s pra iniciar os testes de login")

        pausedLog("Preenchendo o e-mail", 500)

        onView(withId(R.id.editTextEmail)).perform(replaceText("williandrade@gmail.com"))

        pausedLog("Preenchendo a senha", 500)
        onView(withId(R.id.editTextPassword)).perform(replaceText("teste123"))

        pausedLog("Confirmando o login", 500)
        onView(withId(R.id.btLogin)).perform(click())

        pausedLog("Aguardando 5s para confirmar o login e mostrar a tela", 5000)

        pausedLog("Teste final: Verifica TextView na tela")
        onView(withId(R.id.tvBuscarNaRegiao)).check(matches(isDisplayed()))

    }

    fun pausedLog(message : String, timeSleep : Long = 1000) {
        Log.println (Log.DEBUG, "TEST", "$message | Aguardando ${timeSleep}ms")
        Thread.sleep(timeSleep)
    }

}
