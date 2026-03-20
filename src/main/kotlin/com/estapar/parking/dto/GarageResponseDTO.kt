package com.estapar.parking.dto

data class GarageResponseDTO(
    val garage: List<GarageDTO>,
    val spots: List<SpotDTO>
)


data class GarageDTO(
    val sector: String,
    val basePrice: Double,
    val max_capacity: Int
)

data class SpotDTO(
    val id: Long,
    val sector: String,
    val lat: Double,
    val lng: Double
)