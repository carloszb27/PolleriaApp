package com.example.polleriaappandroid

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.polleriaappandroid.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val nombre = binding.etNombre.text.toString().trim()
            val apellido = binding.etApellido.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val telefono = binding.etTelefono.text.toString().trim()
            val direccion = binding.etDireccion.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isNotEmpty() && nombre.isNotEmpty() && apellido.isNotEmpty() && email.isNotEmpty() && telefono.isNotEmpty() && password.isNotEmpty()) {
                registerUser(username, nombre, apellido, email, telefono, direccion, password)
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(username: String, nombre: String, apellido: String, email: String, telefono: String, direccion: String?, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = Usuario(
                        username = username,
                        nombre = nombre,
                        apellido = apellido,
                        email = email,
                        telefono = telefono,
                        direccion = direccion,
                        password = password.hashCode().toString(),
                        estado = true
                    )
                    PolleriaApplication.database.usuarioDao().insertUsuario(user)
                    Toast.makeText(this, "Registro exitoso.", Toast.LENGTH_SHORT).show()
                    finish() // Cierra la actividad y vuelve a LoginActivity
                } else {
                    Toast.makeText(this, "Error en el registro: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
