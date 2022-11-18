package br.com.fiap.isgood.model.dao

import android.content.Context
import android.content.SharedPreferences
import br.com.fiap.isgood.model.Usuario

object UsuarioDAO : GenericDAO<Usuario, String>() {

    fun getByEmail(email: String): Usuario {
        getSampleData().forEach {
            if (it.email == email)
                return it
        }
        throw Exception("Usuário não encontrado com o e-mail '$email'")
    }

    override fun getById(id: String): Usuario {
        getSampleData().forEach {
            if (it.id == id)
                return it
        }
        throw Exception("Usuario com id $id não foi encontrado")
    }

    override fun getByFilter(filter: String?): ArrayList<Usuario> {
        return getSampleData()
    }

    override fun update(obj: Usuario): Usuario {
        getSampleData().remove(getById(obj.id))
        getSampleData().add(obj)
        return obj
    }

    override fun getSampleData(): ArrayList<Usuario> {
        if (defaultList.size == 0) {
            defaultList.add(
                Usuario(
                    "1",
                    "Willian Andrade",
                    "williandrade@gmail.com",
                    "88034460"
                )
            )
            defaultList.add(
                Usuario(
                    "2",
                    "Mr. Nice",
                    "teste@teste.com.br",
                    "88034900"
                )
            )
            defaultList.add(
                Usuario(
                    "3",
                    "Chatonildo da Silva",
                    "outro@email.com",
                    "88000900"
                )
            )
            defaultList.add(
                Usuario(
                    "4",
                    "1234",
                    "1234",
                    "88034460"
                )
            )

        }
        return defaultList

    }


}