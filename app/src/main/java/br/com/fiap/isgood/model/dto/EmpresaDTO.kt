package br.com.fiap.isgood.model.dto

import com.google.gson.annotations.SerializedName

data class EmpresasDTO (

    @SerializedName("id")
    val id:Integer,
    @SerializedName("nome")
    val nome: String,
    @SerializedName("apresentacao")
    val apresentacao: String,
    @SerializedName("rating")
    val rating: Integer,
    @SerializedName("endereco")
    val endereco: String,
    @SerializedName("cep")
    val cep: String,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("logo")
    val logo: String,
    @SerializedName("url_facebook")
    val url_facebook:String,
    @SerializedName("url_twitter")
    val url_twitter:String,
    @SerializedName("url_instagram")
    val url_instagram:String,
    @SerializedName("url_tripadvisor")
    val url_tripadvisor:String
        ){
}