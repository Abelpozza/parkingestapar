package com.estapar.parking.model

import jakarta.persistence.*

@Entity
data class Spot(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val sector: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    var occupied: Boolean = false
)