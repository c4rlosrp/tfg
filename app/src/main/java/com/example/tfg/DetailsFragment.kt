package com.example.tfg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.tfg.Datos.DatosBolsa
import com.example.tfg.Datos.DatosRopa
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsFragment : Fragment() {

    private lateinit var spinnerTallas: Spinner
    private var identifier: String = MyGlobalValue.myidentifier

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        spinnerTallas = view.findViewById(R.id.spinnerTallas)

        val ropa =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                arguments?.getSerializable("ropa", DatosRopa.Data.Attributes::class.java)

            } else {
                arguments?.getSerializable("ropa") as? DatosRopa.Data.Attributes

            }


        val tvNombrePrenda = view.findViewById<TextView>(R.id.tvNombreRopa)
        val tvColeccion = view.findViewById<TextView>(R.id.tvColeccionRopa)
        val tvPrenda = view.findViewById<TextView>(R.id.tvPrendaRopa)
        val tvMarca = view.findViewById<TextView>(R.id.tvMarcaRopa)
        val tvPrecio = view.findViewById<TextView>(R.id.tvPrecioRopa)
        val ivImagen = view.findViewById<ImageView>(R.id.ivRopa)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        val precio: Double = ropa?.price!!
        tvNombrePrenda.text = ropa?.nombre
        tvPrecio.text = ropa?.price.toString() + "€"
        tvColeccion.text = ropa?.coleccion
        tvMarca.text = ropa?.marca
        tvPrenda.text = ropa?.prenda

        Picasso.get().load(ropa?.url).into(ivImagen)

        val tallas = listOf("XS", "S", "M", "L", "XL")
        val adapter = ArrayAdapter(requireContext(),R.layout.item_spinner, tallas)
        adapter.setDropDownViewResource(R.layout.item_spinner)
        spinnerTallas.adapter = adapter

        var addButton = view.findViewById<TextView>(R.id.btnAdd)
        addButton.setOnClickListener {
            if(identifier != ""){
                val nombre = tvNombrePrenda.text.toString().trim()
                val price = precio
                val talla = spinnerTallas.selectedItem.toString() // Cambiar por la talla seleccionada por el usuario
                val marca = tvMarca.text.toString().trim()
                val url = ropa?.url.toString()
                val idRopa = ropa?.idRopa.toString().trim()

                val data = BagRequest.Data(nombre, talla, identifier, marca, price, url, idRopa)
                val bagRequest = BagRequest(data)

                // Realizar la llamada a la API para crear el usuario
                ApiRest.initService()

                val call = ApiRest.service.postBag(bagRequest)
                call.enqueue(object : Callback<List<DatosBolsa>> {
                    override fun onResponse(
                        call: Call<List<DatosBolsa>>,
                        response: Response<List<DatosBolsa>>
                    ) {
                        if (response.isSuccessful) {
                            // La solicitud fue exitosa
                            val datosUsers = response.body()
                        } else {
                            // La solicitud no fue exitosa
                            Toast.makeText(requireContext(), "Inicia sesión o registrate", Toast.LENGTH_SHORT)
                                .show()
                            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContainer, LoginFragment())?.commit()
                        }
                    }

                    override fun onFailure(call: Call<List<DatosBolsa>>, t: Throwable) {
                        if (isAdded) {
                            // Restablecer los campos de entrada
                            // Ocultar los indicadores de error
                        }
                    }
                })
            }else {
                // La solicitud no fue exitosa
                Toast.makeText(requireContext(), "$identifier", Toast.LENGTH_SHORT)
                    .show()
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContainer, LoginFragment())?.commit()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            activity?.onBackPressed() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }
}
