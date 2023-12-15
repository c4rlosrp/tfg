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
import com.example.tfg.Datos.DatosCompras
import com.example.tfg.R
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompraAdapter(private val data: ArrayList<DatosCompras.Data>) : RecyclerView.Adapter<CompraAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_compra, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvCompra)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvCompraPrice)
        val tvMarca: TextView = itemView.findViewById(R.id.tvCompraMarca)
        val tvTalla: TextView = itemView.findViewById(R.id.tvCompraSize)
        val tvFecha: TextView = itemView.findViewById(R.id.tvCompraFecha)

        fun bind(item: DatosCompras.Data) {
            tvNombre.text = item.attributes.nombreRopa
            tvMarca.text = item.attributes.marca
            tvTalla.text = "Talla: " + item.attributes.talla
            tvPrecio.text = item.attributes.importe.toString() + "€"
            tvFecha.text = item.attributes.fecha

        }

    }
}

