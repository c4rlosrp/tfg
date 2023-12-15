package com.example.tfg.Datos


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DatosRopa(
    val `data`: List<Data>,
    val meta: Meta
) {
    data class Data(
        val attributes: Attributes,
        val id: Int
    ) {
        data class Attributes(
            @SerializedName("Coleccion")
            val coleccion: String,
            val createdAt: String,
            val idRopa: String,
            @SerializedName("Marca")
            val marca: String,
            @SerializedName("Nombre")
            val nombre: String,
            @SerializedName("Nuevo")
            val nuevo: Boolean,
            val outlet: Boolean,
            @SerializedName("Prenda")
            val prenda: String,
            @SerializedName("Price")
            val price: Double,
            val publishedAt: String,
            val section: String,
            val updatedAt: String,
            val url: String
        ) : Serializable
    }

    data class Meta(
        val pagination: Pagination
    ) {
        data class Pagination(
            @SerializedName("Page")
            val page: Int,
            val pageCount: Int,
            val pageSize: Int,
            val total: Int
        )
    }
}