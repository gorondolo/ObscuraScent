package com.example.obscurascent

import java.io.Serializable

data class Perfume(
    val id: String = "",
    val nombre: String = "",
    val marca: String = "",
    val descripcion: String = "",
    val imageUrl: String = "",
    val categoria: String = "",
    val precio: Double = 0.0,
    val ml: Int = 100,
    val disponible: Boolean = true,
    val destacado: Boolean = false,
    val rating: Double = 0.0,
    val ventas: Int = 0,
    val notasTope: List<String> = emptyList(),
    val notasCorazon: List<String> = emptyList(),
    val notasBase: List<String> = emptyList()
) : Serializable