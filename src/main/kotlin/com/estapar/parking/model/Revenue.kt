package com.estapar.parking.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
data class Revenue(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val sector: String = "",
    val date: LocalDate? = null,
    var amount: Double = 0.0
)