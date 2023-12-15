package com.example.tfg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.example.tfg.Containers.ContainerMisComprasFragment
import com.example.tfg.Containers.ContainerNovedadesFragment

class UserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var username = view.findViewById<TextView>(R.id.tvUser)
        username.text = MyGlobalValue.myidentifier


        var logoutButton = view.findViewById<ImageButton>(R.id.btnLogOut)
        logoutButton.setOnClickListener {
            MyGlobalValue.myidentifier = ""
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContainer, LoginFragment())?.commit()
        }
        var misComprasButton = view.findViewById<LinearLayout>(R.id.btnMisCompras)
        misComprasButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContainer, ContainerMisComprasFragment())?.addToBackStack(null)?.commit()

        }
    }
}