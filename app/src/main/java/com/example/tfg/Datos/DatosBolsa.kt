package com.example.tfg.Datos


import com.google.gson.annotations.SerializedName

data class DatosBolsa(
    val `data`: List<Data>,
    val meta: Meta
) {
    data class Data(
        val attributes: Attributes,
        val id: Int
    ) {
        data class Attributes(
            val createdAt: String,
            val idRopa: String,
            val marca: String,
            val nombre: String,
            val price: Double,
            val publishedAt: String,
            val talla: String,
            val updatedAt: String,
            val url: String,
            val username: String
        )
    }

    data class Meta(
        val pagination: Pagination
    ) {
        data class Pagination(
            val page: Int,
            val pageCount: Int,
            val pageSize: Int,
            val total: Int
        )
    }
}