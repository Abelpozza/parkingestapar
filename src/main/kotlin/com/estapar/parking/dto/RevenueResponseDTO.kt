package com.estapar.parking.dto

data class RevenueResponseDTO(
    val amount: Double,
    val timestamp: String,
    val currency: String = "BRL"
)