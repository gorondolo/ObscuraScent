package com.example.obscurascent

import java.io.Serializable

data class CartItem(
    val perfumeId: String = "",
    val nombre: String = "",
    val marca: String = "",
    val imageUrl: String = "",
    val precio: Double = 0.0,
    var cantidad: Int = 1
) : Serializable {
    val total: Double get() = precio * cantidad
}