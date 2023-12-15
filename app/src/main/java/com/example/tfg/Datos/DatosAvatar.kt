package com.example.tfg.Datos

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class DatosAvatar(
    val `data`: List<Data>,
    val meta: Meta
) {
    data class Data(
        val attributes: Attributes,
        val id: Int
    ) {
        data class Attributes(
            @SerializedName("Color")
            val color: String,
            val createdAt: String,
            val idAvatar: String,
            val parte: String,
            val publishedAt: String,
            val updatedAt: String,
            val url: String
        ) : Serializable
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