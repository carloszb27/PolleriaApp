package com.example.polleriaappandroid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.polleriaappandroid.databinding.ItemProductBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.bumptech.glide.Glide

enum class ProductAction {
    EDIT, DELETE
}

class ProductAdapter(
    options: FirebaseRecyclerOptions<Snapshot>,
    private val onProductAction: (Snapshot, ProductAction) -> Unit
) : FirebaseRecyclerAdapter<Snapshot, ProductAdapter.ProductViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int, model: Snapshot) {
        val key = getRef(position).key ?: ""
        val productWithKey = model.copy(id = key)
        holder.bind(productWithKey)
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Snapshot) {
            binding.tvNombre.text = product.nombre
            binding.tvCategoria.text = product.categoria
            binding.tvPrecio.text = product.precio
            Glide.with(binding.root.context)
                .load(product.photoUrl)
                .into(binding.imgPhoto)

            binding.btnEdit.setOnClickListener { onProductAction(product, ProductAction.EDIT) }
            binding.btnDelete.setOnClickListener { onProductAction(product, ProductAction.DELETE) }
        }
    }
}