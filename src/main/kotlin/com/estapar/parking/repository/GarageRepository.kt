package com.estapar.parking.repository

import com.estapar.parking.model.Garage
import org.springframework.data.jpa.repository.JpaRepository

interface GarageRepository : JpaRepository<Garage, String>