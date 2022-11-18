package br.com.fiap.isgood.model

data class Credential (
    val email : String,
    val password : String) {
    lateinit var usuario : Usuario
}