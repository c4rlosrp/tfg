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
import com.example.tfg.Containers.ContainerSearchFragment
import com.example.tfg.Datos.DatosRopa
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OutletsFragment : Fragment() {

    val TAG = "SearchFragment"
    var dataOutlet: ArrayList<DatosRopa.Data> = ArrayList()
    private var adapterOutlet: RopaAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_outlets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ApiRest.initService()

        val exitButton = view.findViewById<ImageButton>(R.id.btnExitOutlets)
        exitButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.mainContainer, ContainerSearchFragment())?.commit()
        }

        (activity as AppCompatActivity).getSupportActionBar()?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).getSupportActionBar()?.setDisplayShowHomeEnabled(false)

        ApiRest.initService()
        callGetRopaByOutlet()

        val mLayoutManagerPullandBear = GridLayoutManager(context, 2)
        val rvPullandBear = view.findViewById<RecyclerView>(R.id.rvOutlets)

        rvPullandBear.layoutManager = mLayoutManagerPullandBear

        adapterOutlet = RopaAdapter(dataOutlet) { ropa ->

            activity?.let {
                val fragment = DetailsFragment()
                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("ropa", ropa)

                it.supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.containerOutlets, fragment).commit()
            }
        }
        rvPullandBear.adapter = adapterOutlet as RopaAdapter

    }

    private fun callGetRopaByOutlet() {
        val call = ApiRest.service.getByOutlet(true)
        call.enqueue(object : Callback<DatosRopa> {
            override fun onResponse(
                call: Call<DatosRopa>,
                response: Response<DatosRopa>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    val filteredData = body.data.filter { it.attributes.outlet == true }
                    val sortedData = filteredData.sortedBy { it.attributes.nombre }
                    dataOutlet.clear()
                    dataOutlet.addAll(sortedData)
                    adapterOutlet?.notifyDataSetChanged()
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