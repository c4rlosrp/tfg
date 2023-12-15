package com.example.tfg.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg.Datos.DatosRopa
import com.example.tfg.R
import com.squareup.picasso.Picasso

class RopaAdapter (private val data: ArrayList<(DatosRopa.Data)>, val onClick:(DatosRopa.Data.Attributes) ->Unit) : RecyclerView.Adapter<RopaAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gender, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
    override fun getItemCount(): Int = data.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvNombre = itemView.findViewById<TextView>(R.id.tvGender)
        val tvPrecio = itemView.findViewById<TextView>(R.id.tvGenderPrice)
        val ivGender = itemView.findViewById<ImageView>(R.id.ivGender)

        fun bind(item: DatosRopa.Data) {

            tvNombre.text = item.attributes.nombre
            tvPrecio.text = item.attributes.price.toString() + "€"

            Picasso.get().load(item.attributes.url).into(ivGender)

            itemView.setOnClickListener {
                onClick(item.attributes)
            }
        }
    }
}