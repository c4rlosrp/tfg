package com.example.tfg

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg.Adapters.BolsaAdapter
import com.example.tfg.Datos.DatosBolsa
import com.example.tfg.Datos.DatosCompras
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MyBagFragment : Fragment(){

    private var identifier: String = MyGlobalValue.myidentifier
    private val TAG = "MyBagFragment"
    private val dataBolsa: ArrayList<DatosBolsa.Data> = ArrayList()
    private var adapter: BolsaAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_bag, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ApiRest.initService()

        callGetBolsaUser()

        val rvBag = view.findViewById<RecyclerView>(R.id.rvMyBag)
        adapter = BolsaAdapter(dataBolsa)
        rvBag.adapter = adapter

        val mLayoutManager = GridLayoutManager(context, 1)

        rvBag.layoutManager = mLayoutManager

        val compraButton = view.findViewById<TextView>(R.id.btnCompra)
        compraButton.setOnClickListener {
            if (dataBolsa.isNotEmpty()) {
                for (item in dataBolsa) {
                    val dateTimeString = getCurrentDateTime()
                    val itemId = item.id
                    val data = CompraRequest.Data(
                        nombre_ropa = item.attributes.nombre,
                        talla = item.attributes.talla,
                        username = identifier,
                        marca = item.attributes.marca,
                        importe = item.attributes.price,
                        fecha = dateTimeString
                    )
                    val compraRequest = CompraRequest(data)

                    // Realizar la llamada a la API para crear la compra
                    val call = ApiRest.service.postCompra(compraRequest)
                    call.enqueue(object : Callback<List<DatosCompras>> {
                        override fun onResponse(
                            call: Call<List<DatosCompras>>,
                            response: Response<List<DatosCompras>>
                        ) {
                            if (response.isSuccessful) {
                                // La solicitud fue exitosa, realiza las acciones necesarias
                                val datosCompras = response.body()
                                // Realiza las acciones necesarias después de la compra exitosa
                            } else {
                                // La solicitud no fue exitosa
                                Toast.makeText(
                                    requireContext(),
                                    "Error al realizar la compra",
                                    Toast.LENGTH_SHORT
                                ).show()
                                // Realiza las acciones necesarias después del error en la compra
                            }
                        }

                        override fun onFailure(call: Call<List<DatosCompras>>, t: Throwable) {
                            // Error de comunicación, manejar el error según sea necesario
                            // Puedes mostrar un mensaje de error, registrar el error, etc.
                        }
                    })
                    deleteItem(itemId)

                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "No hay elementos en la bolsa",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun callGetBolsaUser() {
        val call = ApiRest.service.getBagUser(identifier)
        call.enqueue(object : Callback<DatosBolsa> {
            override fun onResponse(
                call: Call<DatosBolsa>,
                response: Response<DatosBolsa>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        val filteredData = body.data.filter { it.attributes.username == identifier }
                        val sortedData = filteredData.sortedBy { it.attributes.nombre }
                        dataBolsa.clear()
                        dataBolsa.addAll(sortedData)
                        adapter?.notifyDataSetChanged()
                        // Imprimir aquí el listado con logs
                    } else {
                        Log.e(TAG, "Response body is null")
                    }
                } else {
                    Log.e(TAG, response.errorBody()?.string() ?: "Error")
                }
            }

            override fun onFailure(call: Call<DatosBolsa>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }
        })
    }

    private fun deleteItem(itemId: Int) {
        val call = ApiRest.service.deleteItemById(itemId)
        call.enqueue(object : Callback<DatosBolsa> {
            override fun onResponse(call: Call<DatosBolsa>, response: Response<DatosBolsa>) {
                if (response.isSuccessful) {
                    // Eliminación exitosa, realiza las acciones necesarias (actualizar la vista, etc.)
                    val item = dataBolsa.find { it.id == itemId }
                    if (item != null) {
                        dataBolsa.remove(item)
                        adapter?.notifyDataSetChanged()
                    }
                } else {
                    // Error al eliminar el elemento, manejar el error según sea necesario
                    // Puedes mostrar un mensaje de error, registrar el error, etc.
                    Log.e(TAG, "Error al eliminar el elemento con ID: $itemId")
                }
            }

            override fun onFailure(call: Call<DatosBolsa>, t: Throwable) {
                // Error de comunicación, manejar el error según sea necesario
                // Puedes mostrar un mensaje de error, registrar el error, etc.
                Log.e(TAG, "Error de comunicación al eliminar el elemento con ID: $itemId")
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDateTime(): String {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        return currentDateTime.format(formatter)
    }

}

