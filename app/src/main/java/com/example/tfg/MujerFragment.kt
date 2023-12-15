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
import com.example.tfg.Datos.DatosRopa
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MujerFragment : Fragment() {

    val TAG = "HomeFragment"
    var dataCamisetas: ArrayList<DatosRopa.Data> = ArrayList()
    var dataPantalones: ArrayList<DatosRopa.Data> = ArrayList()
    var dataSudaderas: ArrayList<DatosRopa.Data> = ArrayList()
    var dataVestidos: ArrayList<DatosRopa.Data> = ArrayList()
    private var adapterCamisetas: RopaAdapter? = null
    private var adapterPantalones: RopaAdapter? = null
    private var adapterSudaderas: RopaAdapter? = null
    private var adapterVestidos: RopaAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_mujer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ApiRest.initService()

        val exitButton = view.findViewById<ImageButton>(R.id.btnExitMujeres)
        exitButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        val ivCamisetas = view.findViewById<ImageView>(R.id.ivCamisetasMujeres)
        val ivPantalones = view.findViewById<ImageView>(R.id.ivPantalonesMujeres)
        val ivSudaderas = view.findViewById<ImageView>(R.id.ivSudaderasMujeres)
        val ivVestidos = view.findViewById<ImageView>(R.id.ivVestidosMujeres)

        Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1686510872/Pose_mujer_camisetas_fe7bd6f1e3.jpg").into(ivCamisetas)
        Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1686510872/small_Pose_mujer_pantalones_f1acfe37a4.jpg").into(ivPantalones)
        Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1686510872/small_Pose_mujer_sudaderas_09927148b8.jpg").into(ivSudaderas)
        Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1686510872/small_Pose_mujer_vestidos_254f834963.jpg").into(ivVestidos)

        (activity as AppCompatActivity).getSupportActionBar()?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).getSupportActionBar()?.setDisplayShowHomeEnabled(false)

        ApiRest.initService()
        callGetRopaBySectionCamisetas()

        val mLayoutManagerCamisetas = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val rvCamisetas = view.findViewById<RecyclerView>(R.id.rvCamisetasMujeres)

        rvCamisetas.layoutManager = mLayoutManagerCamisetas

        adapterCamisetas = RopaAdapter(dataCamisetas) { ropa ->

            activity?.let {
                val fragment = DetailsFragment()
                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("ropa", ropa)

                it.supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.containerMujer, fragment).commit()
            }
        }
        rvCamisetas.adapter = adapterCamisetas as RopaAdapter

        callGetRopaBySectionPantalones()

        val mLayoutManagerPantalones = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val rvPantalones = view.findViewById<RecyclerView>(R.id.rvPantalonesMujeres)

        rvPantalones.layoutManager = mLayoutManagerPantalones

        adapterPantalones = RopaAdapter(dataPantalones) { ropa ->

            activity?.let {
                val fragment = DetailsFragment()
                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("ropa", ropa)

                it.supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.containerMujer, fragment).commit()
            }
        }
        rvPantalones.adapter = adapterPantalones as RopaAdapter

        callGetRopaBySectionSudaderas()

        val mLayoutManagerSudaderas = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val rvSudaderas = view.findViewById<RecyclerView>(R.id.rvSudaderasMujeres)

        rvSudaderas.layoutManager = mLayoutManagerSudaderas

        adapterSudaderas = RopaAdapter(dataSudaderas) { ropa ->

            activity?.let {
                val fragment = DetailsFragment()
                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("ropa", ropa)

                it.supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.containerMujer, fragment).commit()
            }
        }
        rvSudaderas.adapter = adapterSudaderas as RopaAdapter

        callGetRopaBySectionVestidos()

        val mLayoutManagerVestidos = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val rvVestidos = view.findViewById<RecyclerView>(R.id.rvVestidosMujeres)

        rvVestidos.layoutManager = mLayoutManagerVestidos

        adapterVestidos = RopaAdapter(dataVestidos) { ropa ->

            activity?.let {
                val fragment = DetailsFragment()
                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("ropa", ropa)

                it.supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.containerMujer, fragment).commit()
            }
        }
        rvVestidos.adapter = adapterVestidos as RopaAdapter

    }

    private fun callGetRopaBySectionCamisetas() {
        val call = ApiRest.service.getBySectionAndPrenda("Mujer", "Camiseta/top")
        call.enqueue(object : Callback<DatosRopa> {
            override fun onResponse(
                call: Call<DatosRopa>,
                response: Response<DatosRopa>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    val filteredData = body.data.filter { it.attributes.section == "Mujer" && it.attributes.prenda == "Camiseta/top"}
                    val sortedData = filteredData.sortedBy { it.attributes.nombre }
                    dataCamisetas.clear()
                    dataCamisetas.addAll(sortedData)
                    adapterCamisetas?.notifyDataSetChanged()
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

    private fun callGetRopaBySectionPantalones() {
        val call = ApiRest.service.getBySectionAndPrenda("Mujer", "Jeans")
        call.enqueue(object : Callback<DatosRopa> {
            override fun onResponse(
                call: Call<DatosRopa>,
                response: Response<DatosRopa>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    val filteredData = body.data.filter { it.attributes.section == "Mujer" && it.attributes.prenda == "Jeans"}
                    val sortedData = filteredData.sortedBy { it.attributes.nombre }
                    dataPantalones.clear()
                    dataPantalones.addAll(sortedData)
                    adapterPantalones?.notifyDataSetChanged()
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

    private fun callGetRopaBySectionSudaderas() {
        val call = ApiRest.service.getBySectionAndPrenda("Mujer", "Sudadera")
        call.enqueue(object : Callback<DatosRopa> {
            override fun onResponse(
                call: Call<DatosRopa>,
                response: Response<DatosRopa>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    val filteredData = body.data.filter { it.attributes.section == "Mujer" && it.attributes.prenda == "Sudadera"}
                    val sortedData = filteredData.sortedBy { it.attributes.nombre }
                    dataSudaderas.clear()
                    dataSudaderas.addAll(sortedData)
                    adapterSudaderas?.notifyDataSetChanged()
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

    private fun callGetRopaBySectionVestidos() {
        val call = ApiRest.service.getBySectionAndPrenda("Mujer", "Vestido")
        call.enqueue(object : Callback<DatosRopa> {
            override fun onResponse(
                call: Call<DatosRopa>,
                response: Response<DatosRopa>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    val filteredData = body.data.filter { it.attributes.section == "Mujer" && it.attributes.prenda == "Vestido"}
                    val sortedData = filteredData.sortedBy { it.attributes.nombre }
                    dataVestidos.clear()
                    dataVestidos.addAll(sortedData)
                    adapterVestidos?.notifyDataSetChanged()
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