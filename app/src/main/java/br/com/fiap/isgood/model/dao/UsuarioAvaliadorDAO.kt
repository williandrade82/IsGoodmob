package br.com.fiap.isgood.model.dao

import br.com.fiap.isgood.model.UsuarioAvaliador

object UsuarioAvaliadorDAO : GenericDAO<UsuarioAvaliador, String>() {
    override fun getById(id: String): UsuarioAvaliador {
        getSampleData().forEach {
            if (it.id == id)
                return it
        }
        throw Exception("Usuário avaliador não encontrado com o ID $id")
    }

    override fun getByFilter(filter: String?): ArrayList<UsuarioAvaliador> {
        return getSampleData()
    }

    override fun update(obj: UsuarioAvaliador): UsuarioAvaliador {
        defaultList.remove(getById(obj.id))
        defaultList.add(obj)
        return obj
    }

    override fun getSampleData(): ArrayList<UsuarioAvaliador> {
        if (defaultList.size == 0) {
            val arrUsr = UsuarioDAO.getByFilter()

            for (usr in arrUsr) {
                defaultList.add(
                    UsuarioAvaliador(
                        usr.id,
                        usr.name,
                        usr.email,
                        usr.cep
                    )
                )
            }
        }
        return defaultList
    }
}
