package com.example.tfg

data class UserRequest(
    val nombre: String,
    val apellidos: String,
    val username: String,
    val password: String,
    val email: String,
    val idUsername: String
)