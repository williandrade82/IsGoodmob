package br.com.fiap.isgood.model

data class Lanche(
    val id: String,
    val nome: String,
    val descricao: String,
    val strFotoLanche:String,
    val rating:Int,
    val restaurante: Restaurante
) {
    companion object {

    }
}