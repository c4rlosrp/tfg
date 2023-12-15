package com.example.tfg

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.tfg.Containers.ContainerAvatarFragment
import com.example.tfg.Containers.ContainerBagFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), LoginFragment.LoginDataListener,
    AvatarFragment.LeftPopupFragment.OnIndigoSelectedListener,AvatarFragment.RightPopupFragment.OnIndigoBottomSelectedListener {
    private var isLoggedIn: Boolean = false
    private var identifier: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottom_navigation_view = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

        bottom_navigation_view.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_home -> {
                    goToFragment(HomeFragment())
                    true
                }
                R.id.action_avatar -> {
                    goToFragment(AvatarFragment())
                    true
                }
                R.id.action_bag -> {
                    if (isLoggedIn) {

                        lifecycleScope.launch {
                            val hasFavorites = withContext(Dispatchers.IO) {
                                getBagUser(MyGlobalValue.myidentifier)
                            }

                            if (hasFavorites) {
                                goToFragment(ContainerBagFragment())
                            } else {
                                goToFragment(BagFragment())
                            }
                        }
                    } else {
                        goToFragment(BagFragment())
                    }
                    true
                }
                R.id.action_user -> {
                    if (isLoggedIn && MyGlobalValue.myidentifier != "") {
                        goToFragment(UserFragment())
                    } else {
                        goToFragment(LoginFragment())
                    }
                    true
                }
                else -> false
            }
        }

        bottom_navigation_view.selectedItemId = R.id.action_home
    }

    override fun onLoginDataAvailable(identifier: String, password: String) {
        this.identifier = identifier
        isLoggedIn = true
    }

    private fun goToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.mainContainer, fragment)?.commit()
    }

    override fun onIndigoSelected() {
        // Lógica para recargar el AvatarFragment
        val fragment = supportFragmentManager.findFragmentById(R.id.mainContainer)
        if (fragment is AvatarFragment) {
            fragment.reloadAvatar()
        }
    }

    override fun onIndigoBottomSelected() {
        val fragment = supportFragmentManager.findFragmentById(R.id.mainContainer)
        if (fragment is AvatarFragment) {
            fragment.reloadBottomClothes()
        }
    }

    private fun getBagUser(Username: String): Boolean {
        var hasFavorites = false

        ApiRest.initService()
        val call = ApiRest.service.getBagUser(Username)
        val response = call.execute()

        if (response.isSuccessful) {
            val datosFavoritos = response.body()
            // Verificar si se recibieron datos de favoritos válidos y si la lista de favoritos no está vacía
            if (datosFavoritos != null && datosFavoritos.data.isNotEmpty()) {
                Log.d("Tag", Username)
                hasFavorites = datosFavoritos.data.any { it.attributes.username == Username }
            }
        }

        return hasFavorites
    }
}