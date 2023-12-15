package com.example.tfg

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg.Adapters.BolsaAdapter
import com.example.tfg.Adapters.CompraAdapter
import com.example.tfg.Datos.DatosBolsa
import com.example.tfg.Datos.DatosCompras
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MisComprasFragment : Fragment() {

    private var identifier: String = MyGlobalValue.myidentifier
    private val TAG = "UserFragment"
    private val dataCompra: ArrayList<DatosCompras.Data> = ArrayList()
    private var adapter: CompraAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mis_compras, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val exitButton = view.findViewById<ImageButton>(R.id.btnBackMisCompras)
        exitButton.setOnClickListener{
            activity?.supportFragmentManager?.popBackStack()
        }

        ApiRest.initService()

        callGetBolsaUser()

        val rvBag = view.findViewById<RecyclerView>(R.id.rvMyBag)
        adapter = CompraAdapter(dataCompra)
        rvBag.adapter = adapter

        val mLayoutManager = GridLayoutManager(context, 1)

        rvBag.layoutManager = mLayoutManager
    }

    private fun callGetBolsaUser() {
        val call = ApiRest.service.getCompraUser(identifier)
        call.enqueue(object : Callback<DatosCompras> {
            override fun onResponse(
                call: Call<DatosCompras>,
                response: Response<DatosCompras>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        val filteredData = body.data.filter { it.attributes.username == identifier }
                        val sortedData = filteredData.sortedBy { it.attributes.username }
                        dataCompra.clear()
                        dataCompra.addAll(sortedData)
                        adapter?.notifyDataSetChanged()
                        // Imprimir aquí el listado con logs
                    } else {
                        Log.e(TAG, "Response body is null")
                    }
                } else {
                    Log.e(TAG, response.errorBody()?.string() ?: "Error")
                }
            }

            override fun onFailure(call: Call<DatosCompras>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }
        })
    }
}