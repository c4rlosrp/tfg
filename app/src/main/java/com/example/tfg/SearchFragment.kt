package com.example.tfg

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg.Adapters.RopaAdapter
import com.example.tfg.Containers.*
import com.example.tfg.Datos.DatosRopa
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    val TAG = "HomeFragment"
    var dataColecciones: ArrayList<DatosRopa.Data> = ArrayList()
    var dataMarcas: ArrayList<DatosRopa.Data> = ArrayList()
    var dataOutlets: ArrayList<DatosRopa.Data> = ArrayList()
    var dataNovedades: ArrayList<DatosRopa.Data> = ArrayList()
    private var adapterColecciones: RopaAdapter? = null
    private var adapterMarcas: RopaAdapter? = null
    private var adapterOutlets: RopaAdapter? = null
    private var adapterNovedades: RopaAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val exitButton = view.findViewById<ImageButton>(R.id.btnExitSearch)
        exitButton.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContainer, HomeFragment())?.commit()
        }

        val coleccionesButton =  view.findViewById<LinearLayout>(R.id.btnSearchColecciones)
        coleccionesButton.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContainer, ContainerColeccionesFragment())?.addToBackStack(null)?.commit()
        }

        val marcasButton =  view.findViewById<LinearLayout>(R.id.btnSearchMarcas)
        marcasButton.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContainer, ContainerMarcasFragment())?.addToBackStack(null)?.commit()
        }

        val outletsButton =  view.findViewById<LinearLayout>(R.id.btnSearchOutlets)
        outletsButton.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContainer, ContainerOutletsFragment())?.addToBackStack(null)?.commit()
        }

        val novedadesButton =  view.findViewById<LinearLayout>(R.id.btnSearchNovedades)
        novedadesButton.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContainer, ContainerNovedadesFragment())?.addToBackStack(null)?.commit()
        }

        val verTodoColeccionesButton = view.findViewById<LinearLayout>(R.id.btnVerTodoColecciones)
        verTodoColeccionesButton.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContainer, ContainerColeccionesFragment())?.addToBackStack(null)?.commit()
        }

        val verTodoNoveMarcasButton = view.findViewById<LinearLayout>(R.id.btnVerTodoMarcas)
        verTodoNoveMarcasButton.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContainer, ContainerMarcasFragment())?.addToBackStack(null)?.commit()
        }

        val verTodoOutletsButton = view.findViewById<LinearLayout>(R.id.btnVerTodoOutlets)
        verTodoOutletsButton.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContainer, ContainerOutletsFragment())?.addToBackStack(null)?.commit()
        }

        val verTodoNovedadesButton = view.findViewById<LinearLayout>(R.id.btnVerTodoNovedades)
        verTodoNovedadesButton.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContainer, ContainerNovedadesFragment())?.addToBackStack(null)?.commit()
        }

        val search = view.findViewById<SearchView>(R.id.svSearch)
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.lowercase().equals("hombre", ignoreCase = true)) {
                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContainer, ContainerHombreFragment())?.addToBackStack(null)?.commit()
                }else if(query?.lowercase().equals("mujer", ignoreCase = true)){
                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContainer, ContainerMujerFragment())?.addToBackStack(null)?.commit()
                }else if(query?.lowercase().equals("niños", ignoreCase = true)){
                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContainer, ContainerNinosFragment())?.addToBackStack(null)?.commit()
                }else if(query?.lowercase().equals("novedades", ignoreCase = true)){
                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContainer, ContainerNovedadesFragment())?.addToBackStack(null)?.commit()
                }else if(query?.lowercase().equals("novedades niños", ignoreCase = true)){
                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContainer, ContainerNovedadesNinosFragment())?.addToBackStack(null)?.commit()
                }else if(query?.lowercase().equals("novedades hombre", ignoreCase = true)){
                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContainer, ContainerNovedadesHombresFragment())?.addToBackStack(null)?.commit()
                }else if(query?.lowercase().equals("novedades mujer", ignoreCase = true)){
                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContainer, ContainerNovedadesMujeresFragment())?.addToBackStack(null)?.commit()
                }else{
                    Toast.makeText(requireContext(), "No se encontraron resultados", Toast.LENGTH_SHORT).show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Aquí puedes realizar una búsqueda en tiempo real
                // mientras el usuario escribe en el SearchView.
                // Actualiza la RecyclerView con los resultados.
                return true
            }
        })

        ApiRest.initService()
        callGetRopaByColecciones()

        val mLayoutManagerColecciones = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val rvColecciones = view.findViewById<RecyclerView>(R.id.rvColeccionesSearch)

        rvColecciones.layoutManager = mLayoutManagerColecciones

        adapterColecciones = RopaAdapter(dataColecciones) { ropa ->

            activity?.let {
                val fragment = DetailsFragment()
                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("ropa", ropa)

                it.supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.containerSearch, fragment).commit()
            }
        }
        rvColecciones.adapter = adapterColecciones as RopaAdapter

        callGetRopaByMarcas()

        val mLayoutManagerMarcas = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val rvMarcas = view.findViewById<RecyclerView>(R.id.rvMarcasSearch)

        rvMarcas.layoutManager = mLayoutManagerMarcas

        adapterMarcas = RopaAdapter(dataMarcas) { ropa ->

            activity?.let {
                val fragment = DetailsFragment()
                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("ropa", ropa)

                it.supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.containerSearch, fragment).commit()
            }
        }
        rvMarcas.adapter = adapterMarcas as RopaAdapter

        callGetRopaByOutlets()

        val mLayoutManagerOutlets = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val rvOutlets = view.findViewById<RecyclerView>(R.id.rvOutletsSearch)

        rvOutlets.layoutManager = mLayoutManagerOutlets

        adapterOutlets = RopaAdapter(dataOutlets) { ropa ->

            activity?.let {
                val fragment = DetailsFragment()
                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("ropa", ropa)

                it.supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.containerSearch, fragment).commit()
            }
        }
        rvOutlets.adapter = adapterOutlets as RopaAdapter

        callGetRopaByNovedades()

        val mLayoutManagerNovedades = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val rvNovedades = view.findViewById<RecyclerView>(R.id.rvNovedadesSearch)

        rvNovedades.layoutManager = mLayoutManagerNovedades

        adapterNovedades = RopaAdapter(dataNovedades) { ropa ->

            activity?.let {
                val fragment = DetailsFragment()
                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("ropa", ropa)

                it.supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.containerSearch, fragment).commit()
            }
        }
        rvNovedades.adapter = adapterNovedades as RopaAdapter

        (activity as AppCompatActivity).getSupportActionBar()?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).getSupportActionBar()?.setDisplayShowHomeEnabled(false)

    }

    private fun callGetRopaByColecciones() {
        val call = ApiRest.service.getRopaLimit()
        call.enqueue(object : Callback<DatosRopa> {
            override fun onResponse(
                call: Call<DatosRopa>,
                response: Response<DatosRopa>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    val filteredData = body.data
                    val sortedData = filteredData.sortedBy { it.attributes.coleccion }
                    dataColecciones.clear()
                    dataColecciones.addAll(sortedData)
                    adapterColecciones?.notifyDataSetChanged()
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

    private fun callGetRopaByMarcas() {
        val call = ApiRest.service.getRopaLimit()
        call.enqueue(object : Callback<DatosRopa> {
            override fun onResponse(
                call: Call<DatosRopa>,
                response: Response<DatosRopa>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    val filteredData = body.data
                    val sortedData = filteredData.sortedBy { it.attributes.marca }
                    dataMarcas.clear()
                    dataMarcas.addAll(sortedData)
                    adapterMarcas?.notifyDataSetChanged()
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

    private fun callGetRopaByOutlets() {
        val call = ApiRest.service.getByOutletLimit(true)
        call.enqueue(object : Callback<DatosRopa> {
            override fun onResponse(
                call: Call<DatosRopa>,
                response: Response<DatosRopa>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    val filteredData = body.data.filter { it.attributes.outlet == true}
                    val sortedData = filteredData.sortedBy { it.attributes.nombre }
                    dataOutlets.clear()
                    dataOutlets.addAll(sortedData)
                    adapterOutlets?.notifyDataSetChanged()
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

    private fun callGetRopaByNovedades() {
        val call = ApiRest.service.getByNuevoLimit(true)
        call.enqueue(object : Callback<DatosRopa> {
            override fun onResponse(
                call: Call<DatosRopa>,
                response: Response<DatosRopa>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    val filteredData = body.data.filter { it.attributes.nuevo == true}
                    val sortedData = filteredData.sortedBy { it.attributes.nombre }
                    dataNovedades.clear()
                    dataNovedades.addAll(sortedData)
                    adapterNovedades?.notifyDataSetChanged()
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