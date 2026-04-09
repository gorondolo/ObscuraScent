package com.example.obscurascent

object CartManager {
    val items = mutableListOf<CartItem>()

    fun addItem(perfume: Perfume) {
        val existing = items.find { it.perfumeId == perfume.id }
        if (existing != null) {
            existing.cantidad++
        } else {
            items.add(
                CartItem(
                    perfumeId = perfume.id,
                    nombre = perfume.nombre,
                    marca = perfume.marca,
                    imageUrl = perfume.imageUrl,
                    precio = perfume.precio
                )
            )
        }
    }

    fun getTotal() = items.sumOf { it.total }
    fun getCount() = items.sumOf { it.cantidad }
    fun clear() = items.clear()
}