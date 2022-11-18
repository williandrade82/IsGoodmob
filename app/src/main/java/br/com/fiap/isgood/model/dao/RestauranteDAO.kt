package br.com.fiap.isgood.model.dao

import br.com.fiap.isgood.apis.IsGoodApis
import br.com.fiap.isgood.config.RetrofitInstaceFactory
import br.com.fiap.isgood.model.Restaurante
import br.com.fiap.isgood.model.dto.EmpresasDTO
import retrofit2.Call
import retrofit2.Response

object RestauranteDAO : GenericDAO<Restaurante, String>() {
    var restaurantes:ArrayList<Restaurante> = ArrayList<Restaurante>();
    override fun getById(objId: String): Restaurante {
        for (restaurante in restaurantes)
            if (restaurante.id.equals(objId))
                return restaurante
        throw Exception("Não foi encontrado restaurante com o ID $objId")
    }

    override fun getByFilter(filter: String?): ArrayList<Restaurante> {
        val searchResult = arrayListOf<Restaurante>()
        for (restaurante in restaurantes)
            if (restaurante.nome.contains(filter ?: ""))
                searchResult.add(restaurante)
        return searchResult
    }

    override fun update(obj: Restaurante): Restaurante {
        delete(getById(obj.id))
        return add(obj)
    }

    override fun getSampleData(): ArrayList<Restaurante> {
        TODO("Not yet implemented")
    }
}
