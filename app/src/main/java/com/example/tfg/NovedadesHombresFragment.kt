package com.example.tfg

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg.Adapters.RopaAdapter
import com.example.tfg.Containers.ContainerNovedadesFragment
import com.example.tfg.Datos.DatosRopa
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NovedadesHombresFragment : Fragment() {

    val TAG = "NovedadesFragment"
    var data: ArrayList<DatosRopa.Data> = ArrayList()
    private var adapter: RopaAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_novedades_hombres, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ApiRest.initService()

        val exitButton = view.findViewById<ImageButton>(R.id.btnExitNewsHombres)
        exitButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        (activity as AppCompatActivity).getSupportActionBar()?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).getSupportActionBar()?.setDisplayShowHomeEnabled(false)

        callGetRopaBySection()

        val mLayoutManager = GridLayoutManager(context, 2)
        val rvHombre = view.findViewById<RecyclerView>(R.id.rvTodasNovedadesHombres)

        rvHombre.layoutManager = mLayoutManager

        adapter = RopaAdapter(data) { ropa ->

            activity?.let {
                val fragment = DetailsFragment()
                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("ropa", ropa)

                it.supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.containerNovedadesHombres, fragment).commit()
            }
        }
        rvHombre.adapter = adapter as RopaAdapter
    }

    private fun callGetRopaBySection() {
        val call = ApiRest.service.getByNuevoAndSection(true, "Hombre")
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
                    data.clear()
                    data.addAll(sortedData)
                    adapter?.notifyDataSetChanged()
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