package dgtic.unam.modulosiete.Fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import dgtic.unam.modulosiete.MainActivity
import dgtic.unam.modulosiete.databinding.FragmentLoginBinding
import dgtic.unam.modulosiete.ProviderType
import dgtic.unam.modulosiete.R

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var buttonLogin: Button
    private lateinit var buttonSignUp: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        // Observador de acciones de usuario:
        observer()
    }

    private fun observer() {
        buttonSignUp = binding.SignUp
        buttonLogin = binding.SignIn

        // Observacion de acciones en botones:
        buttonLogin.setOnClickListener {
            // Proceso de autenticacion por Firebase
            dataInput()
        }
    }

    private fun message(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun dataInput() {
        firebaseAuth = FirebaseAuth.getInstance()

        val email = binding.usuario.text.toString()
        val pass = binding.password.text.toString()
        val message : String?
        val typeResult = verifyAuth(email, pass)

        // Envio, comprobacion de datos ingresados correctamente y creacion de cuenta
        if (typeResult.equals("SUCCESS"))
            accessAccount(email, pass)
        else {
            message = "Algunos campos se encuentran vacios, por favor ingrese todos los datos."
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun verifyAuth(email: String?, pass: String?) : String {
        if (email?.isNotEmpty() == true && pass?.isNotEmpty() == true)
            return "SUCCESS"
        return "FAIL_EMPTY"
    }

    private fun accessAccount(email: String, pass: String) {
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful) {
                showHome(it.result.user!!.email!!, ProviderType.BASIC)
            } else {
                showAlert("Email inválido o contraseña erronea",
                    "Por favor revise e ingrese las credenciales de nuevo.",
                    "Aceptar")
            }
        }
    }

    private fun showAlert(title: String, message: String, buttonAction: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(buttonAction, null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String, provider: ProviderType) {
        val homeIntent = Intent(requireContext(), MainActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }

        // Se asigna una bandera que indique se queda limpio el stack de activities
        homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(homeIntent)
    }

}