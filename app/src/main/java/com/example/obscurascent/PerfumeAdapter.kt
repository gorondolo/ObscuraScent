package com.example.obscurascent

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.obscurascent.databinding.ViewholderPerfumeBinding

class PerfumeAdapter(
    private val context: Context,
    private val perfumes: List<Perfume>
) : RecyclerView.Adapter<PerfumeAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ViewholderPerfumeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderPerfumeBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val perfume = perfumes[position]
        with(holder.binding) {
            tvNombre.text = perfume.nombre
            tvMarca.text = perfume.marca
            tvPrecio.text = "$${String.format("%,.0f", perfume.precio)} COP"
            Glide.with(context)
                .load(perfume.imageUrl)
                .centerCrop()
                .into(imgPerfume)
            root.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("perfume", perfume)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = perfumes.size
}