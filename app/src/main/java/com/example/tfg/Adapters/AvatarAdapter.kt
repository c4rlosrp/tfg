package com.example.tfg.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg.Datos.DatosAvatar
import com.example.tfg.R
import com.squareup.picasso.Picasso

class AvatarAdapter (private val data: ArrayList<(DatosAvatar.Data)>, val onClick:(DatosAvatar.Data.Attributes) ->Unit) : RecyclerView.Adapter<AvatarAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_avatar, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
    override fun getItemCount(): Int = data.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvAvatar = itemView.findViewById<TextView>(R.id.tvAvatarRecycler)
        val ivAvatar = itemView.findViewById<ImageView>(R.id.ivAvatarRecycler)

        fun bind(item: DatosAvatar.Data) {

            tvAvatar.text = item.attributes.color

            Picasso.get().load(item.attributes.url).into(ivAvatar)

            itemView.setOnClickListener {
                onClick(item.attributes)
            }
        }
    }
}