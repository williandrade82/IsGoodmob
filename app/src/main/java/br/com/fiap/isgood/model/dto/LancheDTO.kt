package br.com.fiap.isgood.model.dto

import com.google.gson.annotations.SerializedName

data class LancheDTO (
    @SerializedName("id")
    val id:Integer,
    @SerializedName("nome")
    val nome: String,
    @SerializedName("descricao")
    val descricao: String,
    @SerializedName("rating")
    val rating: Integer,
    @SerializedName("foto")
    val foto: String,
    @SerializedName("empresa")
    val empresa: EmpresasDTO
        ){
}