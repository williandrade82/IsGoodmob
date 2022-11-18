package br.com.fiap.isgood.apis

import br.com.fiap.isgood.model.dto.EmpresasDTO
import br.com.fiap.isgood.model.dto.LancheDTO
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

interface IsGoodApis {
    @GET("/empresa/listar")
    fun getEmpresas(): Call<List<EmpresasDTO>>

    @GET("/produto/listar")
    fun getLanches(): Call<List<LancheDTO>>
}