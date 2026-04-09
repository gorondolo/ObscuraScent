package com.example.obscurascent

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.obscurascent.databinding.ViewholderCartBinding

class CartAdapter(
    private val context: Context,
    private val items: MutableList<CartItem>,
    private val onTotalChanged: (Double) -> Unit
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ViewholderCartBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderCartBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            tvCartMarca.text = item.marca
            tvCartNombre.text = item.nombre
            tvCartPrecio.text = "$${String.format("%,.0f", item.total)} COP"
            tvCantidad.text = item.cantidad.toString()

            Glide.with(context)
                .load(item.imageUrl)
                .centerCrop()
                .into(imgCartItem)

            btnIncrease.setOnClickListener {
                item.cantidad++
                tvCantidad.text = item.cantidad.toString()
                tvCartPrecio.text = "$${String.format("%,.0f", item.total)} COP"
                onTotalChanged(calculateTotal())
            }

            btnDecrease.setOnClickListener {
                if (item.cantidad > 1) {
                    item.cantidad--
                    tvCantidad.text = item.cantidad.toString()
                    tvCartPrecio.text = "$${String.format("%,.0f", item.total)} COP"
                    onTotalChanged(calculateTotal())
                } else {
                    items.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, items.size)
                    onTotalChanged(calculateTotal())
                }
            }
        }
    }

    private fun calculateTotal() = items.sumOf { it.total }

    override fun getItemCount() = items.size
}
