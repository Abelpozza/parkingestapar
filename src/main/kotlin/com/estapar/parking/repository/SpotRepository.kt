package com.estapar.parking.repository

import com.estapar.parking.model.Spot
import org.springframework.data.jpa.repository.JpaRepository

interface SpotRepository : JpaRepository<Spot, Long> {
    fun findBySectorAndOccupied(sector: String, occupied: Boolean): List<Spot>
    fun findByOccupied(occupied: Boolean): List<Spot>
}