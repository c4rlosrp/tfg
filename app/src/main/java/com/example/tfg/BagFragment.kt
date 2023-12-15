package com.example.tfg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.tfg.Containers.ContainerNovedadesFragment

class BagFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_bag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsButton = view.findViewById<TextView>(R.id.btnNovedades)
        newsButton.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContainer, ContainerNovedadesFragment())?.addToBackStack(null)?.commit()
        }

    }
}