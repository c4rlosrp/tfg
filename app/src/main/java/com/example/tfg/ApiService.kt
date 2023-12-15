package com.example.tfg


import com.example.tfg.Datos.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("ropas?pagination[pageSize]=200")
    fun getRopa(): Call<DatosRopa>

    @GET("ropas?pagination[pageSize]=8")
    fun getRopaLimit(): Call<DatosRopa>

    @GET("ropas?pagination[pageSize]=200")
    fun getBySectionAndPrenda(
        @Query("Section") section: String,
        @Query("Prenda") prenda: String
    ): Call<DatosRopa>

    @GET("ropas?pagination[pageSize]=200")
    fun getByPrenda(
        @Query("Prenda") prenda: String
    ): Call<DatosRopa>

    @GET("ropas?pagination[pageSize]=200")
    fun getByMarca(
        @Query("Marca") marca: String
    ): Call<DatosRopa>

    @GET("ropas?pagination[pageSize]=200")
    fun getByColeccion(
        @Query("Coleccion") coleccion: String
    ): Call<DatosRopa>

    @GET("ropas?pagination[pageSize]=10")
    fun getByNuevoLimit(
        @Query("Nuevo") nuevo: Boolean
    ): Call<DatosRopa>

    @GET("ropas?pagination[pageSize]=15")
    fun getByNuevoFavs(
        @Query("Nuevo") nuevo: Boolean
    ): Call<DatosRopa>

    @GET("ropas?pagination[pageSize]=20")
    fun getByNuevoAndSectionLimit(
        @Query("Nuevo") nuevo: Boolean,
        @Query("Section") section: String
    ): Call<DatosRopa>

    @GET("ropas?pagination[pageSize]=200")
    fun getByNuevoAndSectionLimitNinos(
        @Query("Nuevo") nuevo: Boolean,
        @Query("Section") section: String
    ): Call<DatosRopa>

    @GET("ropas?pagination[pageSize]=200")
    fun getByNuevoAndSection(
        @Query("Nuevo") nuevo: Boolean,
        @Query("Section") section: String
    ): Call<DatosRopa>

    @GET("ropas?pagination[pageSize]=35")
    fun getByOutletLimit(
        @Query("Outlet") outlet: Boolean
    ): Call<DatosRopa>

    @GET("ropas?pagination[pageSize]=200")
    fun getByOutlet(
        @Query("Outlet") outlet: Boolean,
    ): Call<DatosRopa>

    @POST("auth/local")
    fun login(@Body credentials: LoginRequest): Call<List<DatosUsers>>

    @POST("auth/local/register")
    fun postUser(@Body request: UserRequest): Call<List<DatosUsers>>

    @GET("bolsas?pagination[pageSize]=200")
    fun getBagUser(
        @Query("username") username: String
    ): Call<DatosBolsa>

    @GET("compras?pagination[pageSize]=200")
    fun getCompraUser(
        @Query("username") username: String
    ): Call<DatosCompras>

    @GET("avatars?pagination[pageSize]=200")
    fun getAvatarTop(
        @Query("parte") parte: String
    ): Call<DatosAvatar>

    @GET("avatars?pagination[pageSize]=200")
    fun getAvatarBottom(
        @Query("parte") parte: String
    ): Call<DatosAvatar>

    @POST("bolsas")
    fun postBag(@Body request: BagRequest): Call<List<DatosBolsa>>

    @POST("compras")
    fun postCompra(@Body request: CompraRequest): Call<List<DatosCompras>>

    @DELETE("bolsas/{itemId}")
    fun deleteItem(@Path("itemId") itemId: Int): Call<DatosBolsa>

    @DELETE("bolsas/{itemId}")
    fun deleteItemById(@Path("itemId") itemId: Int): Call<DatosBolsa>

}