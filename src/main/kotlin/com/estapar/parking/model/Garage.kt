package com.estapar.parking.model

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Garage(
    @Id
    val sector: String = "",
    val basePrice: Double = 0.0,
    val maxCapacity: Int = 0
)