package com.example.tfg

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg.Adapters.RopaAdapter
import com.example.tfg.Containers.ContainerSearchFragment
import com.example.tfg.Datos.DatosRopa
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarcasFragment : Fragment() {

    val TAG = "SearchFragment"
    var dataPullandBear: ArrayList<DatosRopa.Data> = ArrayList()
    var dataZara: ArrayList<DatosRopa.Data> = ArrayList()
    private var adapterPullandBear: RopaAdapter? = null
    private var adapterZara: RopaAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_marcas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ApiRest.initService()

        val exitButton = view.findViewById<ImageButton>(R.id.btnExitMarcas)
        exitButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.mainContainer, ContainerSearchFragment())?.commit()
        }

        val ivPullandBear = view.findViewById<ImageView>(R.id.ivMarcasPullandBear)
        val ivZara = view.findViewById<ImageView>(R.id.ivMarcasZara)

        Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1686681919/small_Pulland_Bear_logo_4d5209e118.png").into(ivPullandBear)
        Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1686680801/small_Zara_Logo_d94bfbb436.png").into(ivZara)

        (activity as AppCompatActivity).getSupportActionBar()?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).getSupportActionBar()?.setDisplayShowHomeEnabled(false)

        ApiRest.initService()
        callGetRopaByMarcaPullandBear()

        val mLayoutManagerPullandBear = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val rvPullandBear = view.findViewById<RecyclerView>(R.id.rvMarcasPullandBear)

        rvPullandBear.layoutManager = mLayoutManagerPullandBear

        adapterPullandBear = RopaAdapter(dataPullandBear) { ropa ->

            activity?.let {
                val fragment = DetailsFragment()
                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("ropa", ropa)

                it.supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.containerMarcas, fragment).commit()
            }
        }
        rvPullandBear.adapter = adapterPullandBear as RopaAdapter

        callGetRopaByMarcaZara()

        val mLayoutManagerZara = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val rvZara = view.findViewById<RecyclerView>(R.id.rvMarcasZara)

        rvZara.layoutManager = mLayoutManagerZara

        adapterZara = RopaAdapter(dataZara) { ropa ->

            activity?.let {
                val fragment = DetailsFragment()
                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("ropa", ropa)

                it.supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.containerMarcas, fragment).commit()
            }
        }
        rvZara.adapter = adapterZara as RopaAdapter

    }

    private fun callGetRopaByMarcaPullandBear() {
        val call = ApiRest.service.getByMarca("pullandbear")
        call.enqueue(object : Callback<DatosRopa> {
            override fun onResponse(
                call: Call<DatosRopa>,
                response: Response<DatosRopa>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    val filteredData = body.data.filter { it.attributes.marca == "pullandbear"}
                    val sortedData = filteredData.sortedBy { it.attributes.nombre }
                    dataPullandBear.clear()
                    dataPullandBear.addAll(sortedData)
                    adapterPullandBear?.notifyDataSetChanged()
                    // Imprimir aquí el listado con logs
                } else {
                    Log.e(TAG, response.errorBody()?.string() ?: "Error")
                }
            }

            override fun onFailure(call: Call<DatosRopa>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }
        })
    }

    private fun callGetRopaByMarcaZara() {
        val call = ApiRest.service.getByMarca("ZARA")
        call.enqueue(object : Callback<DatosRopa> {
            override fun onResponse(
                call: Call<DatosRopa>,
                response: Response<DatosRopa>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    val filteredData = body.data.filter { it.attributes.marca == "ZARA"}
                    val sortedData = filteredData.sortedBy { it.attributes.nombre }
                    dataZara.clear()
                    dataZara.addAll(sortedData)
                    adapterZara?.notifyDataSetChanged()
                    // Imprimir aquí el listado con logs
                } else {
                    Log.e(TAG, response.errorBody()?.string() ?: "Error")
                }
            }

            override fun onFailure(call: Call<DatosRopa>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }
        })
    }
}