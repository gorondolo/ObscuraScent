package com.example.obscurascent

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.obscurascent.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter
    private val cartItems = mutableListOf<CartItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackCart.setOnClickListener { finish() }

        loadCart()

        binding.btnCheckout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("¡Gracias por tu compra!")
                .setMessage("Tu pedido ha sido recibido. Lo procesaremos a la brevedad.")
                .setPositiveButton("Aceptar") { _, _ ->
                    CartManager.clear()
                    cartItems.clear()
                    cartAdapter.notifyDataSetChanged()
                    updateTotal(0.0)
                    finish()
                }
                .show()
        }
    }

    private fun loadCart() {
        cartItems.addAll(CartManager.items)
        cartAdapter = CartAdapter(this, cartItems) { total ->
            updateTotal(total)
        }
        binding.recyclerCart.layoutManager = LinearLayoutManager(this)
        binding.recyclerCart.adapter = cartAdapter
        updateTotal(cartItems.sumOf { it.total })
    }

    private fun updateTotal(total: Double) {
        binding.tvSubtotal.text = "$${String.format("%,.0f", total)} COP"
        binding.tvTotal.text = "$${String.format("%,.0f", total)} COP"
    }
}