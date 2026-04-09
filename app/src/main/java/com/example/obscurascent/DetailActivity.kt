package com.example.obscurascent

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.obscurascent.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var perfume: Perfume? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        perfume = intent.getSerializableExtra("perfume") as? Perfume
        perfume?.let { loadDetail(it) }

        binding.btnBack.setOnClickListener { finish() }

        binding.btnAgregarCarrito.setOnClickListener {
            perfume?.let {
                CartManager.addItem(it)
                Toast.makeText(
                    this,
                    "${it.nombre} agregado al carrito",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun loadDetail(perfume: Perfume) {
        with(binding) {
            tvDetalleMarca.text = perfume.marca
            tvDetalleNombre.text = perfume.nombre
            tvDetallePrecio.text = "$${String.format("%,.0f", perfume.precio)} COP"
            tvDetalleDescripcion.text = perfume.descripcion
            tvRating.text = "${perfume.rating}"
            tvVentas.text = " · ${perfume.ventas} vendidos"
            tvNotasTope.text = perfume.notasTope.joinToString(" · ")
            tvNotasCorazon.text = perfume.notasCorazon.joinToString(" · ")
            tvNotasBase.text = perfume.notasBase.joinToString(" · ")
            Glide.with(this@DetailActivity)
                .load(perfume.imageUrl)
                .centerCrop()
                .into(imgDetalle)
        }
    }
}