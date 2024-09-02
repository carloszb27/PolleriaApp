import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.polleriaappandroid.Usuario
import com.example.polleriaappandroid.databinding.ItemUsuarioBinding

enum class UsuarioAction {
    EDIT, DELETE
}

class UsuarioAdapter(private val onUsuarioAction: (Usuario, UsuarioAction) -> Unit) :
    ListAdapter<Usuario, UsuarioAdapter.UsuarioViewHolder>(UsuarioDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val binding = ItemUsuarioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsuarioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = getItem(position)
        holder.bind(usuario)
    }

    inner class UsuarioViewHolder(private val binding: ItemUsuarioBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(usuario: Usuario) {
            binding.tvUsername.text = usuario.username
            binding.tvNombre.text = "${usuario.nombre} ${usuario.apellido}"
            binding.tvEmail.text = usuario.email
            binding.btnEdit.setOnClickListener { onUsuarioAction(usuario, UsuarioAction.EDIT) }
            binding.btnDelete.setOnClickListener { onUsuarioAction(usuario, UsuarioAction.DELETE) }
        }
    }

    class UsuarioDiffCallback : DiffUtil.ItemCallback<Usuario>() {
        override fun areItemsTheSame(oldItem: Usuario, newItem: Usuario): Boolean {
            return oldItem.idusuario == newItem.idusuario
        }

        override fun areContentsTheSame(oldItem: Usuario, newItem: Usuario): Boolean {
            return oldItem == newItem
        }
    }
}