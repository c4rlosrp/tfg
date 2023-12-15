package com.example.tfg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.tfg.Datos.DatosUsers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistroFragment : Fragment() {

    private lateinit var nombreEditText: EditText
    private lateinit var apellidosEditText: EditText
    private lateinit var usuarioEditText: EditText
    private lateinit var correoEditText: EditText
    private lateinit var password1EditText: EditText
    private lateinit var password2EditText: EditText
    private lateinit var cbRegistro: CheckBox
    private lateinit var llObligatorioNombre: LinearLayout
    private lateinit var llObligatorioApellidos: LinearLayout
    private lateinit var llObligatorioUsuario: LinearLayout
    private lateinit var llObligatorioCorreo: LinearLayout
    private lateinit var llObligatorioPassword1: LinearLayout
    private lateinit var llObligatorioPassword2: LinearLayout
    private lateinit var llObligatorioCB: LinearLayout
    private lateinit var btnCrearCuenta: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nombreEditText = view.findViewById(R.id.etNombre)
        apellidosEditText = view.findViewById(R.id.etApellidos)
        usuarioEditText = view.findViewById(R.id.etUsuario)
        correoEditText = view.findViewById(R.id.etCorreo)
        password1EditText = view.findViewById(R.id.etPassword1)
        password2EditText = view.findViewById(R.id.etPassword2)
        cbRegistro = view.findViewById(R.id.cbRegistro)
        llObligatorioNombre = view.findViewById(R.id.llObligatorioNombre)
        llObligatorioApellidos = view.findViewById(R.id.llObligatorioApellidos)
        llObligatorioUsuario = view.findViewById(R.id.llObligatorioUsuario)
        llObligatorioCorreo = view.findViewById(R.id.llObligatorioCorreo)
        llObligatorioPassword1 = view.findViewById(R.id.llObligatorioPassword1)
        llObligatorioPassword2 = view.findViewById(R.id.llObligatorioPassword2)
        llObligatorioCB = view.findViewById(R.id.llObligatorioCB)
        btnCrearCuenta = view.findViewById(R.id.btnCrearCuenta)

        val exitButton = view.findViewById<ImageButton>(R.id.btnBack)
        exitButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.mainContainer, LoginFragment())?.commit()
        }

        val createAcountButton = view.findViewById<TextView>(R.id.btnCrearCuenta)
        createAcountButton.setOnClickListener {
            val passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$".toRegex()
            val nombre = nombreEditText.text.toString().trim()
            val apellidos = apellidosEditText.text.toString().trim()
            val usuario = usuarioEditText.text.toString().trim()
            val correo = correoEditText.text.toString().trim()
            val password1 = password1EditText.text.toString().trim()
            val password2 = password2EditText.text.toString().trim()
            val idUsername = usuario.replace("[^a-zA-Z0-9]".toRegex(), "").toLowerCase()

            resetErrorStates()

            if (nombre.isEmpty()) {
                llObligatorioNombre.visibility = View.VISIBLE
            }else{
                val marginInDp = 23 // Valor del margen en dp
                val marginInPx = (marginInDp * resources.displayMetrics.density).toInt()

                val layoutParams = nombreEditText.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.bottomMargin = marginInPx
                nombreEditText.layoutParams = layoutParams
            }

            if (apellidos.isEmpty()) {
                llObligatorioApellidos.visibility = View.VISIBLE
            }else{
                val marginInDp = 23 // Valor del margen en dp
                val marginInPx = (marginInDp * resources.displayMetrics.density).toInt()

                val layoutParams = nombreEditText.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.bottomMargin = marginInPx
                apellidosEditText.layoutParams = layoutParams
            }

            if (usuario.isEmpty()) {
                llObligatorioUsuario.visibility = View.VISIBLE
            }else{
                val marginInDp = 23 // Valor del margen en dp
                val marginInPx = (marginInDp * resources.displayMetrics.density).toInt()

                val layoutParams = nombreEditText.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.bottomMargin = marginInPx
                usuarioEditText.layoutParams = layoutParams
            }

            if (correo.isEmpty()) {
                llObligatorioCorreo.visibility = View.VISIBLE
            }
            else{
                val marginInDp = 23 // Valor del margen en dp
                val marginInPx = (marginInDp * resources.displayMetrics.density).toInt()

                val layoutParams = nombreEditText.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.bottomMargin = marginInPx
                correoEditText.layoutParams = layoutParams
            }

            if (password1.isEmpty() && passwordPattern.matches(password1)) {
                llObligatorioPassword1.visibility = View.VISIBLE
            }
            else{
                val marginInDp = 23 // Valor del margen en dp
                val marginInPx = (marginInDp * resources.displayMetrics.density).toInt()

                val layoutParams = nombreEditText.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.bottomMargin = marginInPx
                password1EditText.layoutParams = layoutParams
            }

            if (password2.isEmpty() && passwordPattern.matches(password1) && password2 != password1) {
                llObligatorioPassword2.visibility = View.VISIBLE
            }
            else{
                val marginInDp = 23 // Valor del margen en dp
                val marginInPx = (marginInDp * resources.displayMetrics.density).toInt()

                val layoutParams = nombreEditText.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.bottomMargin = marginInPx
                password2EditText.layoutParams = layoutParams
            }

            if (!cbRegistro.isChecked) {
                llObligatorioCB.visibility = View.VISIBLE
            }else{
                val marginInDp = 23 // Valor del margen en dp
                val marginInPx = (marginInDp * resources.displayMetrics.density).toInt()

                val layoutParams = nombreEditText.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.bottomMargin = marginInPx
                cbRegistro.layoutParams = layoutParams
            }

            if (nombre.isNotEmpty() && apellidos.isNotEmpty() && usuario.isNotEmpty() && correo.isNotEmpty()
                && password1.isNotEmpty() && password2.isNotEmpty() && cbRegistro.isChecked) {
                // Todos los campos obligatorios están completos y el checkbox está marcado

                // Aquí puedes realizar las acciones necesarias para crear la cuenta del usuario
                // por ejemplo, llamar a una función para enviar los datos al servidor

                val userRequest = UserRequest(nombre, apellidos, usuario, password2, correo, idUsername)

                // Realizar la llamada a la API para crear el usuario
                ApiRest.initService()

                val call = ApiRest.service.postUser(userRequest)
                call.enqueue(object : Callback<List<DatosUsers>> {
                    override fun onResponse(
                        call: Call<List<DatosUsers>>,
                        response: Response<List<DatosUsers>>
                    ) {
                        if (response.isSuccessful) {
                            // La solicitud fue exitosa
                            val datosUsers = response.body()
                            // Manejar los datos recibidos según sea necesario
                            nombreEditText.text.clear()
                            apellidosEditText.text.clear()
                            usuarioEditText.text.clear()
                            correoEditText.text.clear()
                            password1EditText.text.clear()
                            password2EditText.text.clear()
                            cbRegistro.isChecked = false

                            resetErrorStates()
                            // Verificar si el fragmento sigue adjunto antes de mostrar el Toast
                            Toast.makeText(requireContext(), "Cuenta creada con éxito", Toast.LENGTH_SHORT).show()
                            activity?.supportFragmentManager?.beginTransaction()
                                ?.replace(R.id.mainContainer, LoginFragment())?.commit()
                        } else {
                            // La solicitud no fue exitosa
                            Toast.makeText(requireContext(), "Error al crear el usuario", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<List<DatosUsers>>, t: Throwable) {
                        if (isAdded) {
                            // Restablecer los campos de entrada


                            // Ocultar los indicadores de error

                        }
                    }
                })

                // Mostrar un mensaje de éxito o realizar alguna otra acción
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.mainContainer, LoginFragment())?.commit()
            } else {
                Toast.makeText(requireContext(), "Completa todos los campos obligatorios", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun resetErrorStates() {
        llObligatorioNombre.visibility = View.GONE
        llObligatorioApellidos.visibility = View.GONE
        llObligatorioUsuario.visibility = View.GONE
        llObligatorioCorreo.visibility = View.GONE
        llObligatorioPassword1.visibility = View.GONE
        llObligatorioPassword2.visibility = View.GONE
        llObligatorioCB.visibility = View.GONE
    }

}