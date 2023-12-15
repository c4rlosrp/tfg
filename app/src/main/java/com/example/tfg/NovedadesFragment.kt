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
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg.Adapters.RopaAdapter
import com.example.tfg.Containers.ContainerNovedadesHombresFragment
import com.example.tfg.Containers.ContainerNovedadesMujeresFragment
import com.example.tfg.Containers.ContainerNovedadesNinosFragment
import com.example.tfg.Datos.DatosRopa
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NovedadesFragment : Fragment() {

    val TAG = "BagFragment"
    var dataHombre: ArrayList<DatosRopa.Data> = ArrayList()
    var dataMujer: ArrayList<DatosRopa.Data> = ArrayList()
    var dataNinos: ArrayList<DatosRopa.Data> = ArrayList()
    private var adapterHombre: RopaAdapter? = null
    private var adapterMujer: RopaAdapter? = null
    private var adapterNinos: RopaAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_novedades, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ApiRest.initService()

        val exitButton = view.findViewById<ImageButton>(R.id.btnExitNews)
        exitButton.setOnClickListener{
            activity?.supportFragmentManager?.popBackStack()
        }

        val verTodoHombres = view.findViewById<LinearLayout>(R.id.btnVerTodoHombres)
        verTodoHombres.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContainer, ContainerNovedadesHombresFragment())?.commit()
        }

        val verTodoMujeres = view.findViewById<LinearLayout>(R.id.btnVerTodoMujeres)
        verTodoMujeres.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContainer, ContainerNovedadesMujeresFragment())?.commit()
        }

        val verTodoNinos = view.findViewById<LinearLayout>(R.id.btnVerTodoNinos)
        verTodoNinos.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContainer, ContainerNovedadesNinosFragment())?.commit()
        }
        val ivNovedadesHombres = view.findViewById<ImageView>(R.id.ivNovedadesHombres)
        val ivNovedadesMujeres = view.findViewById<ImageView>(R.id.ivNovedadesMujeres)
        val ivNovedadesNinos = view.findViewById<ImageView>(R.id.ivNovedadesNinos)

        Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1686688440/small_pose_novedades_hombres_8704122edd.jpg").into(ivNovedadesHombres)
        Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1686688406/small_pose_mujer_novedades_ed5c300fc1.jpg").into(ivNovedadesMujeres)
        Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1686688457/small_pose_novedades_ninos_e80a2eb201.png").into(ivNovedadesNinos)

        (activity as AppCompatActivity).getSupportActionBar()?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).getSupportActionBar()?.setDisplayShowHomeEnabled(false)

        callGetRopaBySectionHombre()

        val mLayoutManagerHombre = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val rvHombre = view.findViewById<RecyclerView>(R.id.rvNovedadesHombres)

        rvHombre.layoutManager = mLayoutManagerHombre

        adapterHombre = RopaAdapter(dataHombre) { ropa ->

            activity?.let {
                val fragment = DetailsFragment()
                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("ropa", ropa)

                it.supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.containerNovedades, fragment).commit()
            }
        }
        rvHombre.adapter = adapterHombre as RopaAdapter

        callGetRopaBySectionMujer()

        val mLayoutManagerMujer = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val rvMujer = view.findViewById<RecyclerView>(R.id.rvNovedadesMujeres)

        rvMujer.layoutManager = mLayoutManagerMujer

        adapterMujer = RopaAdapter(dataMujer) { ropa ->

            activity?.let {
                val fragment = DetailsFragment()
                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("ropa", ropa)

                it.supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.containerNovedades, fragment).commit()
            }
        }
        rvMujer.adapter = adapterMujer as RopaAdapter

        callGetRopaBySectionNinos()

        val mLayoutManagerNinos = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val rvNinos = view.findViewById<RecyclerView>(R.id.rvNovedadesNinos)

        rvNinos.layoutManager = mLayoutManagerNinos

        adapterNinos = RopaAdapter(dataNinos) { ropa ->

            activity?.let {
                val fragment = DetailsFragment()
                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("ropa", ropa)

                it.supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.containerNovedades, fragment).commit()
            }
        }
        rvNinos.adapter = adapterNinos as RopaAdapter

        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.app_name)
        (activity as? AppCompatActivity)?.supportActionBar?.setBackgroundDrawable(
            ColorDrawable(Color.parseColor("#282828"))
        )
    }

    private fun callGetRopaBySectionHombre() {
        val call = ApiRest.service.getByNuevoAndSectionLimit(true, "Hombre")
        call.enqueue(object : Callback<DatosRopa> {
            override fun onResponse(
                call: Call<DatosRopa>,
                response: Response<DatosRopa>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    val filteredData = body.data.filter { it.attributes.nuevo == true && it.attributes.section == "Hombre"}
                    val sortedData = filteredData.sortedBy { it.attributes.nombre }
                    dataHombre.clear()
                    dataHombre.addAll(sortedData)
                    adapterHombre?.notifyDataSetChanged()
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

    private fun callGetRopaBySectionMujer() {
        val call = ApiRest.service.getByNuevoAndSectionLimit(true, "Mujer")
        call.enqueue(object : Callback<DatosRopa> {
            override fun onResponse(
                call: Call<DatosRopa>,
                response: Response<DatosRopa>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    val filteredData = body.data.filter { it.attributes.nuevo == true && it.attributes.section == "Mujer"}
                    val sortedData = filteredData.sortedBy { it.attributes.nombre }
                    dataMujer.clear()
                    dataMujer.addAll(sortedData)
                    adapterMujer?.notifyDataSetChanged()
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

    private fun callGetRopaBySectionNinos() {
        val call = ApiRest.service.getByNuevoAndSectionLimitNinos(true, "Ninos")
        call.enqueue(object : Callback<DatosRopa> {
            override fun onResponse(
                call: Call<DatosRopa>,
                response: Response<DatosRopa>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    val filteredData = body.data.filter { it.attributes.nuevo == true && it.attributes.section == "Ninos"}
                    val sortedData = filteredData.sortedBy { it.attributes.nombre }
                    dataNinos.clear()
                    dataNinos.addAll(sortedData)
                    adapterNinos?.notifyDataSetChanged()
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