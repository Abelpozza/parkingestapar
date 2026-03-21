package com.estapar.parking.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Vehicle(
    @Id
    val licensePlate: String = "",
    val sector: String = "",
    val entryTime: LocalDateTime? = null,
    var exitTime: LocalDateTime? = null,
    var price: Double? = null,
    val spotId: Long? = null
)