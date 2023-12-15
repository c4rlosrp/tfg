package com.example.tfg

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg.Adapters.RopaAdapter
import com.example.tfg.Datos.DatosRopa
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HombreFragment : Fragment() {

    val TAG = "MainActivity"
    var dataCamisetas: ArrayList<DatosRopa.Data> = ArrayList()
    var dataPantalones: ArrayList<DatosRopa.Data> = ArrayList()
    var dataSudaderas: ArrayList<DatosRopa.Data> = ArrayList()
    private var adapterCamisetas: RopaAdapter? = null
    private var adapterPantalones: RopaAdapter? = null
    private var adapterSudaderas: RopaAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hombre, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ApiRest.initService()

        val exitButton = view.findViewById<ImageButton>(R.id.btnExitHombres)
        exitButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
        val ivCamisetas = view.findViewById<ImageView>(R.id.ivCamisetasHombre)
        val ivPantalones = view.findViewById<ImageView>(R.id.ivPantalonesHombre)
        val ivSudaderas = view.findViewById<ImageView>(R.id.ivSudaderasHombre)

        Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1686504125/small_Pose_hombre_camisetas_f9b958d47f.jpg").into(ivCamisetas)
        Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1686505473/small_Pose_hombre_pantalones_07e4cfb95d.webp").into(ivPantalones)
        Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1686507424/small_Pose_hombre_sudadera_eadd2f284c.jpg").into(ivSudaderas)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(false)

        callGetRopaBySectionCamisetas()

        val mLayoutManagerCamisetas = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val rvCamisetas = view.findViewById<RecyclerView>(R.id.rvCamisetasHombres)

        rvCamisetas.layoutManager = mLayoutManagerCamisetas

        adapterCamisetas = RopaAdapter(dataCamisetas) { ropa ->
            activity?.let {
                val fragment = DetailsFragment()
                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("ropa", ropa)

                it.supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.containerHombre, fragment).commit()
            }
        }
        rvCamisetas.adapter = adapterCamisetas as RopaAdapter

        callGetRopaBySectionPantalones()

        val mLayoutManagerPantalones = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val rvPantalones = view.findViewById<RecyclerView>(R.id.rvPantalonesHombres)

        rvPantalones.layoutManager = mLayoutManagerPantalones

        adapterPantalones = RopaAdapter(dataPantalones) { ropa ->
            activity?.let {
                val fragment = DetailsFragment()
                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("ropa", ropa)

                it.supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.containerHombre, fragment).commit()
            }
        }

        rvPantalones.adapter = adapterPantalones as RopaAdapter

        callGetRopaBySectionSudaderas()

        val mLayoutManagerSudaderas = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val rvSudaderas = view.findViewById<RecyclerView>(R.id.rvSudaderasHombres)

        rvSudaderas.layoutManager = mLayoutManagerSudaderas

        adapterSudaderas = RopaAdapter(dataSudaderas) { ropa ->
            activity?.let {
                val fragment = DetailsFragment()
                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("ropa", ropa)

                it.supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.containerHombre, fragment).commit()
            }
        }

        rvSudaderas.adapter = adapterSudaderas as RopaAdapter

        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.app_name)
        (activity as? AppCompatActivity)?.supportActionBar?.setBackgroundDrawable(
            ColorDrawable(Color.parseColor("#282828"))
        )
    }

    private fun callGetRopaBySectionCamisetas() {
        val call = ApiRest.service.getBySectionAndPrenda("Hombre", "Camiseta/top")
        call.enqueue(object : Callback<DatosRopa> {
            override fun onResponse(
                call: Call<DatosRopa>,
                response: Response<DatosRopa>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    val filteredData = body.data.filter { it.attributes.section == "Hombre" && it.attributes.prenda == "Camiseta/top"}
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
        val call = ApiRest.service.getBySectionAndPrenda("Hombre", "Jeans")
        call.enqueue(object : Callback<DatosRopa> {
            override fun onResponse(
                call: Call<DatosRopa>,
                response: Response<DatosRopa>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    val filteredData = body.data.filter { it.attributes.section == "Hombre" && it.attributes.prenda == "Jeans"}
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
        val call = ApiRest.service.getBySectionAndPrenda("Hombre", "Sudadera")
        call.enqueue(object : Callback<DatosRopa> {
            override fun onResponse(
                call: Call<DatosRopa>,
                response: Response<DatosRopa>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    val filteredData = body.data.filter { it.attributes.section == "Hombre" && it.attributes.prenda == "Sudadera"}
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
}

