package com.example.tfg.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg.ApiRest
import com.example.tfg.Datos.DatosBolsa
import com.example.tfg.R
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BolsaAdapter(private val data: ArrayList<DatosBolsa.Data>) : RecyclerView.Adapter<BolsaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bag, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvBag)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvBagPrice)
        val tvMarca: TextView = itemView.findViewById(R.id.tvBagMarca)
        val tvTalla: TextView = itemView.findViewById(R.id.tvBagSize)
        val ivBag: ImageView = itemView.findViewById(R.id.ivBag)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)

        fun bind(item: DatosBolsa.Data) {
            tvNombre.text = item.attributes.nombre
            tvMarca.text = item.attributes.marca
            tvTalla.text = "Talla: " + item.attributes.talla
            tvPrecio.text = item.attributes.price.toString() + "€"

            Picasso.get().load(item.attributes.url).into(ivBag)

            btnDelete.setOnClickListener {
                val itemId = data[adapterPosition].id
                deleteItem(itemId)
            }
        }

        private fun deleteItem(itemId: Int) {
            // Realizar la llamada DELETE a la API aquí
            // Reemplaza "ApiRest.service.deleteItem(itemId)" con el método real de la API para eliminar un elemento por su ID
            val call = ApiRest.service.deleteItem(itemId)
            call.enqueue(object : Callback<DatosBolsa> {
                override fun onResponse(call: Call<DatosBolsa>, response: Response<DatosBolsa>) {
                    if (response.isSuccessful) {
                        // Eliminación exitosa, realiza las acciones necesarias (actualizar la vista, etc.)
                        data.removeAt(adapterPosition)
                        notifyDataSetChanged()
                    } else {
                        // Error al eliminar el elemento, manejar el error según sea necesario
                        // Puedes mostrar un mensaje de error, registrar el error, etc.
                    }
                }

                override fun onFailure(call: Call<DatosBolsa>, t: Throwable) {
                    // Error de comunicación, manejar el error según sea necesario
                    // Puedes mostrar un mensaje de error, registrar el error, etc.
                }
            })
        }
    }
}

