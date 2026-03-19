package com.estapar.parking.repository

import com.estapar.parking.model.Vehicle
import org.springframework.data.jpa.repository.JpaRepository

interface VehicleRepository : JpaRepository<Vehicle, String> {
    fun findBySectorAndExitTimeIsNull(sector: String): List<Vehicle>
}