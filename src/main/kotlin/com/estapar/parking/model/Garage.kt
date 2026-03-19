package com.estapar.parking.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "garagem")
data class Garage(
    @Id
    @Column(name = "sector")
    val sector: String = "",

    @Column(name = "base_price")
    val basePrice: Double = 0.0,

    @Column(name = "max_capacity")
    val maxCapacity: Int = 0
)