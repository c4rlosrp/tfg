package com.example.tfg

data class BagRequest(
    val data: Data
){
    data class Data(
        val nombre: String,
        val talla: String,
        val username: String,
        val marca: String,
        val price: Double,
        val url: String,
        val idRopa: String
    )
}