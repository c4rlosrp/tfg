package com.example.tfg.Datos

data class DatosUsers(
    val jwt: String,
    val user: User
) {
    data class User(
        val apellidos: String,
        val blocked: Boolean,
        val confirmed: Boolean,
        val createdAt: String,
        val email: String,
        val id: Int,
        val idUsername: String,
        val nombre: String,
        val provider: String,
        val updatedAt: String,
        val username: String
    )
}