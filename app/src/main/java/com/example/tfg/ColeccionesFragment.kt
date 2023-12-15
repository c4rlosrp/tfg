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

class ColeccionesFragment : Fragment() {

    val TAG = "SearchFragment"
    var dataOtoño: ArrayList<DatosRopa.Data> = ArrayList()
    var dataInvierno: ArrayList<DatosRopa.Data> = ArrayList()
    var dataPrimavera: ArrayList<DatosRopa.Data> = ArrayList()
    var dataVerano: ArrayList<DatosRopa.Data> = ArrayList()
    var dataAnime: ArrayList<DatosRopa.Data> = ArrayList()
    private var adapterOtoño: RopaAdapter? = null
    private var adapterInvierno: RopaAdapter? = null
    private var adapterPrimavera: RopaAdapter? = null
    private var adapterVerano: RopaAdapter? = null
    private var adapterAnime: RopaAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_colecciones, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ApiRest.initService()

        val exitButton = view.findViewById<ImageButton>(R.id.btnExitColecciones)
        exitButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.mainContainer, ContainerSearchFragment())?.commit()
        }

        val ivOtoño = view.findViewById<ImageView>(R.id.ivOtoño)
        val ivInvierno = view.findViewById<ImageView>(R.id.ivInvierno)
        val ivPrimavera = view.findViewById<ImageView>(R.id.ivPrimavera)
        val ivVerano = view.findViewById<ImageView>(R.id.ivVerano)
        val ivAnime = view.findViewById<ImageView>(R.id.ivAnime)

        Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1686680416/small_pose_otono_15281e7faa.jpg").into(ivOtoño)
        Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1686680416/pose_invierno_494a8a1196.jpg").into(ivInvierno)
        Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1686680416/pose_primavera_53f8828053.jpg").into(ivPrimavera)
        Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1686680416/small_pose_verano_da2bc5651c.jpg").into(ivVerano)
        Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1686680416/small_pose_anime_f95bbddd04.jpg").into(ivAnime)

        (activity as AppCompatActivity).getSupportActionBar()?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).getSupportActionBar()?.setDisplayShowHomeEnabled(false)

        ApiRest.initService()
        callGetRopaByColeccionOtoño()

        val mLayoutManagerOtoño = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val rvOtoño = view.findViewById<RecyclerView>(R.id.rvOtoño)

        rvOtoño.layoutManager = mLayoutManagerOtoño

        adapterOtoño = RopaAdapter(dataOtoño) { ropa ->

            activity?.let {
                val fragment = DetailsFragment()
                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("ropa", ropa)

                it.supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.containerColecciones, fragment).commit()
            }
        }
        rvOtoño.adapter = adapterOtoño as RopaAdapter

        callGetRopaByColeccionInvierno()

        val mLayoutManagerInvierno = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val rvInvierno = view.findViewById<RecyclerView>(R.id.rvInvierno)

        rvInvierno.layoutManager = mLayoutManagerInvierno

        adapterInvierno = RopaAdapter(dataInvierno) { ropa ->

            activity?.let {
                val fragment = DetailsFragment()
                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("ropa", ropa)

                it.supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.containerColecciones, fragment).commit()
            }
        }
        rvInvierno.adapter = adapterInvierno as RopaAdapter

        callGetRopaByColeccionPrimavera()

        val mLayoutManagerPrimavera = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val rvPrimavera = view.findViewById<RecyclerView>(R.id.rvPrimavera)

        rvPrimavera.layoutManager = mLayoutManagerPrimavera

        adapterPrimavera = RopaAdapter(dataPrimavera) { ropa ->

            activity?.let {
                val fragment = DetailsFragment()
                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("ropa", ropa)

                it.supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.containerColecciones, fragment).commit()
            }
        }
        rvPrimavera.adapter = adapterPrimavera as RopaAdapter

        callGetRopaByColeccionVerano()

        val mLayoutManagerVerano = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val rvVerano = view.findViewById<RecyclerView>(R.id.rvVerano)

        rvVerano.layoutManager = mLayoutManagerVerano

        adapterVerano = RopaAdapter(dataVerano) { ropa ->

            activity?.let {
                val fragment = DetailsFragment()
                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("ropa", ropa)

                it.supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.containerColecciones, fragment).commit()
            }
        }
        rvVerano.adapter = adapterVerano as RopaAdapter

        callGetRopaByColeccionAnime()

        val mLayoutManagerAnime = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val rvAnime = view.findViewById<RecyclerView>(R.id.rvAnime)

        rvAnime.layoutManager = mLayoutManagerAnime

        adapterAnime = RopaAdapter(dataAnime) { ropa ->

            activity?.let {
                val fragment = DetailsFragment()
                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("ropa", ropa)

                it.supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.containerColecciones, fragment).commit()
            }
        }
        rvAnime.adapter = adapterAnime as RopaAdapter

    }

    private fun callGetRopaByColeccionOtoño() {
        val call = ApiRest.service.getByColeccion("Otoño")
        call.enqueue(object : Callback<DatosRopa> {
            override fun onResponse(
                call: Call<DatosRopa>,
                response: Response<DatosRopa>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    val filteredData = body.data.filter { it.attributes.coleccion == "Otoño"}
                    val sortedData = filteredData.sortedBy { it.attributes.nombre }
                    dataOtoño.clear()
                    dataOtoño.addAll(sortedData)
                    adapterOtoño?.notifyDataSetChanged()
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

    private fun callGetRopaByColeccionInvierno() {
        val call = ApiRest.service.getByColeccion("Invierno")
        call.enqueue(object : Callback<DatosRopa> {
            override fun onResponse(
                call: Call<DatosRopa>,
                response: Response<DatosRopa>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    val filteredData = body.data.filter { it.attributes.coleccion == "Invierno"}
                    val sortedData = filteredData.sortedBy { it.attributes.nombre }
                    dataInvierno.clear()
                    dataInvierno.addAll(sortedData)
                    adapterInvierno?.notifyDataSetChanged()
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

    private fun callGetRopaByColeccionPrimavera() {
        val call = ApiRest.service.getByColeccion("Primavera")
        call.enqueue(object : Callback<DatosRopa> {
            override fun onResponse(
                call: Call<DatosRopa>,
                response: Response<DatosRopa>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    val filteredData = body.data.filter { it.attributes.coleccion == "Primavera"}
                    val sortedData = filteredData.sortedBy { it.attributes.nombre }
                    dataPrimavera.clear()
                    dataPrimavera.addAll(sortedData)
                    adapterPrimavera?.notifyDataSetChanged()
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

    private fun callGetRopaByColeccionVerano() {
        val call = ApiRest.service.getByColeccion("Verano")
        call.enqueue(object : Callback<DatosRopa> {
            override fun onResponse(
                call: Call<DatosRopa>,
                response: Response<DatosRopa>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    val filteredData = body.data.filter { it.attributes.coleccion == "Verano"}
                    val sortedData = filteredData.sortedBy { it.attributes.nombre }
                    dataVerano.clear()
                    dataVerano.addAll(sortedData)
                    adapterVerano?.notifyDataSetChanged()
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

    private fun callGetRopaByColeccionAnime() {
        val call = ApiRest.service.getByColeccion("Anime")
        call.enqueue(object : Callback<DatosRopa> {
            override fun onResponse(
                call: Call<DatosRopa>,
                response: Response<DatosRopa>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    val filteredData = body.data.filter { it.attributes.coleccion == "Anime"}
                    val sortedData = filteredData.sortedBy { it.attributes.nombre }
                    dataAnime.clear()
                    dataAnime.addAll(sortedData)
                    adapterAnime?.notifyDataSetChanged()
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