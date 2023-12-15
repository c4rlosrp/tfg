package com.example.tfg

data class CompraRequest(
    val data: Data
){
    data class Data(
        val nombre_ropa: String,
        val talla: String,
        val username: String,
        val marca: String,
        val importe: Double,
        val fecha: String,
    )
}
