package com.estapar.parking.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Vehicle(
    @Id
    val licensePlate: String = "",
    var sector: String = "",
    val entryTime: LocalDateTime? = null,
    var exitTime: LocalDateTime? = null,
    var price: Double? = null,
    var spotId: Long? = null
)