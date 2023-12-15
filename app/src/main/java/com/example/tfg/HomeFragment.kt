package com.example.tfg


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tfg.Containers.*
import com.squareup.picasso.Picasso


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ApiRest.initService()

        val searchButton = view.findViewById<ImageButton>(R.id.btnSearch)
        searchButton.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContainer, ContainerSearchFragment())?.commit()
        }

        val hombresButton = view.findViewById<RelativeLayout>(R.id.RLSectionHombre)
        hombresButton.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContainer, ContainerHombreFragment())?.addToBackStack(null)?.commit()
        }

        val mujeresButton = view.findViewById<RelativeLayout>(R.id.RLSectionMujer)
        mujeresButton.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContainer, ContainerMujerFragment())?.addToBackStack(null)?.commit()
        }

        val niñosButton = view.findViewById<RelativeLayout>(R.id.RLSectionNiños)
        niñosButton.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContainer, ContainerNinosFragment())?.addToBackStack(null)?.commit()
        }


        val textoMujer = view.findViewById<TextView>(R.id.tvMujer)
        textoMujer.setText("Mujeres")
        val textoHombre = view.findViewById<TextView>(R.id.tvHombre)
        textoHombre.setText("Hombres")
        val textoNiños = view.findViewById<TextView>(R.id.tvNiños)
        textoNiños.setText("Niños")

        val ivHombre = view.findViewById<ImageView>(R.id.ivHombre)
        val ivMujer = view.findViewById<ImageView>(R.id.ivMujer)
        val ivNiños = view.findViewById<ImageView>(R.id.ivNiños)


        Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1686396048/small_pose_ecabf648f9.jpg").into(ivHombre)
        Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1686396048/small_pose_mujer_b65c6eedf9.jpg").into(ivMujer)
        Picasso.get().load("https://res.cloudinary.com/dpfkjvqn5/image/upload/v1686396048/small_pose_ninos_3ce9ae3c49.jpg").into(ivNiños)

        (activity as? AppCompatActivity)?.supportActionBar?.setBackgroundDrawable(
            ColorDrawable(Color.parseColor("#282828"))
        )
    }
}