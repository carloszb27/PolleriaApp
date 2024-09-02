package com.example.polleriaappandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.polleriaappandroid.databinding.FragmentAddBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var productsRef: DatabaseReference
    private lateinit var adapter: ProductAdapter
    private var editingProductId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productsRef = FirebaseDatabase.getInstance().getReference("snapshots")

        setupRecyclerView()
        setupAddButton()
    }

    private fun setupRecyclerView() {
        val options = FirebaseRecyclerOptions.Builder<Snapshot>()
            .setQuery(productsRef, Snapshot::class.java)
            .build()

        adapter = ProductAdapter(options) { product, action ->
            when (action) {
                ProductAction.EDIT -> editProduct(product)
                ProductAction.DELETE -> deleteProduct(product)
            }
        }

        binding.recyclerViewProducts.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewProducts.adapter = adapter
    }

    private fun setupAddButton() {
        binding.btnAddProduct.setOnClickListener {
            val nombre = binding.etNombre.text.toString()
            val categoria = binding.etCategoria.text.toString()
            val unidadMedida = binding.etUnidadMedida.text.toString()
            val presentacion = binding.etPresentacion.text.toString()
            val precio = binding.etPrecio.text.toString()
            val photoUrl = binding.etPhotoUrl.text.toString()

            if (nombre.isNotEmpty() && categoria.isNotEmpty() && unidadMedida.isNotEmpty() &&
                presentacion.isNotEmpty() && precio.isNotEmpty() && photoUrl.isNotEmpty()
            ) {
                if (editingProductId != null) {
                    updateProduct(editingProductId!!, nombre, categoria, unidadMedida, presentacion, precio, photoUrl)
                } else {
                    addProduct(nombre, categoria, unidadMedida, presentacion, precio, photoUrl)
                }
            } else {
                Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addProduct(nombre: String, categoria: String, unidadMedida: String,
                           presentacion: String, precio: String, photoUrl: String) {
        val newProductRef = productsRef.push()
        val newProductId = newProductRef.key ?: return

        val newProduct = Snapshot(
            id = newProductId,
            nombre = nombre,
            categoria = categoria,
            unidadmedida = unidadMedida,
            presentacion = presentacion,
            precio = precio,
            photoUrl = photoUrl
        )

        newProductRef.setValue(newProduct)
            .addOnSuccessListener {
                Toast.makeText(context, "Producto agregado exitosamente", Toast.LENGTH_SHORT).show()
                clearInputFields()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error al agregar el producto", Toast.LENGTH_SHORT).show()
            }
    }

    private fun editProduct(product: Snapshot) {
        editingProductId = product.id
        binding.etNombre.setText(product.nombre)
        binding.etCategoria.setText(product.categoria)
        binding.etUnidadMedida.setText(product.unidadmedida)
        binding.etPresentacion.setText(product.presentacion)
        binding.etPrecio.setText(product.precio)
        binding.etPhotoUrl.setText(product.photoUrl)

        binding.btnAddProduct.text = "Actualizar Producto"
    }

    private fun updateProduct(id: String, nombre: String, categoria: String, unidadMedida: String,
                              presentacion: String, precio: String, photoUrl: String) {
        val updatedProduct = Snapshot(
            id = id,
            nombre = nombre,
            categoria = categoria,
            unidadmedida = unidadMedida,
            presentacion = presentacion,
            precio = precio,
            photoUrl = photoUrl
        )

        productsRef.child(id).setValue(updatedProduct)
            .addOnSuccessListener {
                Toast.makeText(context, "Producto actualizado exitosamente", Toast.LENGTH_SHORT).show()
                clearInputFields()
                binding.btnAddProduct.text = "Agregar Producto"
                editingProductId = null
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error al actualizar el producto", Toast.LENGTH_SHORT).show()
            }
    }

    private fun deleteProduct(product: Snapshot) {
        product.id?.let { id ->
            productsRef.child(id).removeValue()
                .addOnSuccessListener {
                    Toast.makeText(context, "Producto eliminado exitosamente", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Error al eliminar el producto", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun clearInputFields() {
        binding.etNombre.text.clear()
        binding.etCategoria.text.clear()
        binding.etUnidadMedida.text.clear()
        binding.etPresentacion.text.clear()
        binding.etPrecio.text.clear()
        binding.etPhotoUrl.text.clear()
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}