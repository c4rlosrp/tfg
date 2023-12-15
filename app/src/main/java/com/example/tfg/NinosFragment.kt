package com.example.tfg

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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

class NinosFragment : Fragment() {

    val TAG = "MainActivity"
    var dataCamisetas: ArrayList<DatosRopa.Data> = ArrayList()
    var dataPantalones: ArrayList<DatosRopa.Data> = ArrayList()
    var dataSudaderas: ArrayList<DatosRopa.Data> = ArrayList()
    var dataCazadoras: ArrayList<DatosRopa.Data> = ArrayList()
    private var adapterCamisetas: RopaAdapter? = null
    private var adapterPantalones: RopaAdapter? = null
    private var adapterSudaderas: RopaAdapter? = null
    private var adapterCazadoras: RopaAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_ninos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ApiRest.initService()

        val exitButton = view.findViewById<ImageButton>(R.id.btnExitNinos)
        exitButton.setOnClickListener{
            activity?.supportFragmentManager?.popBackStack()
        }

        val ivCamisetas = view.findViewById<ImageView>(R.id.ivCamisetasNinos)
        val ivPantalones = view.findViewById<ImageView>(R.id.ivPantalonesNinos)
        val ivSudaderas = view.findViewById<ImageView>(R.id.ivSudaderasNinos)
        val ivCazadoras = view.findViewById<ImageView>(R.id.ivCazadorasNinos)

        Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1686679368/Pose_ninos_Camisetas_f395380490.png").into(ivCamisetas)
        Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1686679368/small_Pose_ninos_Pantalones_6b36cc21af.jpg").into(ivPantalones)
        Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1686679368/small_pose_ninos_Sudaderas_da4a1e0838.jpg").into(ivSudaderas)
        Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1686679368/small_pose_ninos_Cazadoras_8ac1657441.jpg").into(ivCazadoras)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(false)

        callGetRopaBySectionCamisetas()

        val mLayoutManagerCamisetas = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val rvCamisetas = view.findViewById<RecyclerView>(R.id.rvCamisetasNinos)

        rvCamisetas.layoutManager = mLayoutManagerCamisetas

        adapterCamisetas = RopaAdapter(dataCamisetas) { ropa ->
            activity?.let {
                val fragment = DetailsFragment()
                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("ropa", ropa)

                it.supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.containerNinos, fragment).commit()
            }
        }
        rvCamisetas.adapter = adapterCamisetas as RopaAdapter

        callGetRopaBySectionPantalones()

        val mLayoutManagerPantalones = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val rvPantalones = view.findViewById<RecyclerView>(R.id.rvPantalonesNinos)

        rvPantalones.layoutManager = mLayoutManagerPantalones

        adapterPantalones = RopaAdapter(dataPantalones) { ropa ->
            activity?.let {
                val fragment = DetailsFragment()
                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("ropa", ropa)

                it.supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.containerNinos, fragment).commit()
            }
        }

        rvPantalones.adapter = adapterPantalones as RopaAdapter

        callGetRopaBySectionSudaderas()

        val mLayoutManagerSudaderas = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val rvSudaderas = view.findViewById<RecyclerView>(R.id.rvSudaderasNinos)

        rvSudaderas.layoutManager = mLayoutManagerSudaderas

        adapterSudaderas = RopaAdapter(dataSudaderas) { ropa ->
            activity?.let {
                val fragment = DetailsFragment()
                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("ropa", ropa)

                it.supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.containerNinos, fragment).commit()
            }
        }

        rvSudaderas.adapter = adapterSudaderas as RopaAdapter

        callGetRopaBySectionCazadoras()

        val mLayoutManagerCazadoras = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val rvCazadoras = view.findViewById<RecyclerView>(R.id.rvCazadorasNinos)

        rvCazadoras.layoutManager = mLayoutManagerCazadoras

        adapterCazadoras = RopaAdapter(dataCazadoras) { ropa ->
            activity?.let {
                val fragment = DetailsFragment()
                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("ropa", ropa)

                it.supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.containerNinos, fragment).commit()
            }
        }

        rvCazadoras.adapter = adapterCazadoras as RopaAdapter

        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.app_name)
        (activity as? AppCompatActivity)?.supportActionBar?.setBackgroundDrawable(
            ColorDrawable(Color.parseColor("#282828"))
        )
    }

    private fun callGetRopaBySectionCamisetas() {
        val call = ApiRest.service.getBySectionAndPrenda("Ninos", "Camiseta/top")
        call.enqueue(object : Callback<DatosRopa> {
            override fun onResponse(
                call: Call<DatosRopa>,
                response: Response<DatosRopa>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    val filteredData = body.data.filter { it.attributes.section == "Ninos" && it.attributes.prenda == "Camiseta/top"}
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
        val call = ApiRest.service.getBySectionAndPrenda("Ninos", "Jeans")
        call.enqueue(object : Callback<DatosRopa> {
            override fun onResponse(
                call: Call<DatosRopa>,
                response: Response<DatosRopa>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    val filteredData = body.data.filter { it.attributes.section == "Ninos" && it.attributes.prenda == "Jeans"}
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
        val call = ApiRest.service.getBySectionAndPrenda("Ninos", "Sudadera")
        call.enqueue(object : Callback<DatosRopa> {
            override fun onResponse(
                call: Call<DatosRopa>,
                response: Response<DatosRopa>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    val filteredData = body.data.filter { it.attributes.section == "Ninos" && it.attributes.prenda == "Sudadera"}
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

    private fun callGetRopaBySectionCazadoras() {
        val call = ApiRest.service.getBySectionAndPrenda("Ninos", "Chaquetas/Cazadoras")
        call.enqueue(object : Callback<DatosRopa> {
            override fun onResponse(
                call: Call<DatosRopa>,
                response: Response<DatosRopa>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    val filteredData = body.data.filter { it.attributes.section == "Ninos" && it.attributes.prenda == "Chaquetas/Cazadoras"}
                    val sortedData = filteredData.sortedBy { it.attributes.nombre }
                    dataCazadoras.clear()
                    dataCazadoras.addAll(sortedData)
                    adapterCazadoras?.notifyDataSetChanged()
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