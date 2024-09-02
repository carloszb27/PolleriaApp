import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.polleriaappandroid.PolleriaApplication
import com.example.polleriaappandroid.Usuario
import com.example.polleriaappandroid.UsuarioDao
import com.example.polleriaappandroid.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var usuarioAdapter: UsuarioAdapter
    private lateinit var usuarioDao: UsuarioDao

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usuarioDao = PolleriaApplication.database.usuarioDao()
        setupRecyclerView()
        setupAddButton()
        loadUsuarios()
    }

    private fun setupRecyclerView() {
        usuarioAdapter = UsuarioAdapter { usuario, action ->
            when (action) {
                UsuarioAction.EDIT -> editUsuario(usuario)
                UsuarioAction.DELETE -> deleteUsuario(usuario)
            }
        }
        binding.recyclerViewUsuarios.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewUsuarios.adapter = usuarioAdapter
    }

    private fun setupAddButton() {
        binding.btnAddUsuario.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val nombre = binding.etNombre.text.toString()
            val apellido = binding.etApellido.text.toString()
            val email = binding.etEmail.text.toString()
            val telefono = binding.etTelefono.text.toString()
            val direccion = binding.etDireccion.text.toString()
            val password = binding.etPassword.text.toString()

            if (username.isNotEmpty() && nombre.isNotEmpty() && apellido.isNotEmpty() &&
                email.isNotEmpty() && telefono.isNotEmpty() && password.isNotEmpty()
            ) {
                val usuario = Usuario(
                    username = username,
                    nombre = nombre,
                    apellido = apellido,
                    email = email,
                    telefono = telefono,
                    direccion = direccion,
                    password = password.hashCode().toString(),
                    estado = true
                )
                addUsuario(usuario)
            } else {
                Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addUsuario(usuario: Usuario) {
        Thread {
            usuarioDao.insertUsuario(usuario)
            activity?.runOnUiThread {
                clearInputFields()
                loadUsuarios()
                Toast.makeText(context, "Usuario agregado exitosamente", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun editUsuario(usuario: Usuario) {
        binding.etUsername.setText(usuario.username)
        binding.etNombre.setText(usuario.nombre)
        binding.etApellido.setText(usuario.apellido)
        binding.etEmail.setText(usuario.email)
        binding.etTelefono.setText(usuario.telefono)
        binding.etDireccion.setText(usuario.direccion)
        binding.etPassword.setText("")  // Por seguridad, no mostramos la contraseña
        binding.btnAddUsuario.text = "Actualizar Usuario"
        binding.btnAddUsuario.setOnClickListener {
            val updatedUsuario = usuario.copy(
                username = binding.etUsername.text.toString(),
                nombre = binding.etNombre.text.toString(),
                apellido = binding.etApellido.text.toString(),
                email = binding.etEmail.text.toString(),
                telefono = binding.etTelefono.text.toString(),
                direccion = binding.etDireccion.text.toString(),
                password = binding.etPassword.text.toString().hashCode().toString()
            )
            updateUsuario(updatedUsuario)
        }
    }

    private fun updateUsuario(usuario: Usuario) {
        Thread {
            usuarioDao.updateUsuario(usuario)
            activity?.runOnUiThread {
                clearInputFields()
                loadUsuarios()
                binding.btnAddUsuario.text = "Agregar Usuario"
                Toast.makeText(context, "Usuario actualizado exitosamente", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun deleteUsuario(usuario: Usuario) {
        Thread {
            usuarioDao.deleteUsuario(usuario)
            activity?.runOnUiThread {
                loadUsuarios()
                Toast.makeText(context, "Usuario eliminado exitosamente", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun loadUsuarios() {
        Thread {
            val usuarios = usuarioDao.getAllUsuarios()
            activity?.runOnUiThread {
                usuarioAdapter.submitList(usuarios)
            }
        }.start()
    }

    private fun clearInputFields() {
        binding.etUsername.text.clear()
        binding.etNombre.text.clear()
        binding.etApellido.text.clear()
        binding.etEmail.text.clear()
        binding.etTelefono.text.clear()
        binding.etDireccion.text.clear()
        binding.etPassword.text.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}