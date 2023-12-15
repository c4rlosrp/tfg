package com.example.tfg

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.tfg.Datos.DatosUsers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment : Fragment() {

    private lateinit var call: Call<List<DatosUsers>>
    private lateinit var identifierEditText: EditText
    private lateinit var passwordEditText: EditText
    var identifier: String = ""
    var password = ""

    interface LoginDataListener {
        fun onLoginDataAvailable(identifier: String, password: String)
    }

    private var loginDataListener: LoginDataListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verificar si el contexto implementa la interfaz LoginDataListener
        if (context is LoginDataListener) {
            loginDataListener = context
        } else {
            throw RuntimeException("$context must implement LoginDataListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        identifierEditText = view.findViewById(R.id.etUserLogin)
        passwordEditText = view.findViewById(R.id.etPasswordLogin)

        val registroButton = view.findViewById<TextView>(R.id.btnRegistro)
        registroButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.mainContainer, RegistroFragment())?.commit()
        }

        val inicioButton = view.findViewById<TextView>(R.id.btnLogin)
        inicioButton.setOnClickListener {

            identifier = identifierEditText.text.toString().trim()
            password = passwordEditText.text.toString().trim()

            val loginRequest = LoginRequest(identifier, password)

            // Realizar la llamada a la API para crear el usuario
            ApiRest.initService()

            call = ApiRest.service.login(loginRequest)
            call.enqueue(object : Callback<List<DatosUsers>> {
                override fun onResponse(
                    call: Call<List<DatosUsers>>,
                    response: Response<List<DatosUsers>>
                ) {
                    if (response.isSuccessful) {
                        // La solicitud fue exitosa
                        val datosUsers = response.body()
                    } else {
                        // La solicitud no fue exitosa
                        Toast.makeText(requireContext(), "Error al iniciar sesión, revisa que hayas escrito bien la contraseña y el usuario", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<DatosUsers>>, t: Throwable) {
                    if (isAdded) {
                        MyGlobalValue.myidentifier = identifier
                        loginDataListener?.onLoginDataAvailable(identifier, password)
                        // Restablecer los campos de entrada
                        identifierEditText.text.clear()
                        passwordEditText.text.clear()

                        // Ocultar los indicadores de error
                        // Verificar si el fragmento sigue adjunto antes de mostrar el Toast
                        Toast.makeText(requireContext(), "Bienvenido $identifier", Toast.LENGTH_SHORT).show()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.mainContainer, UserFragment())?.commit()
                    }
                }
            })
        }

    }
}