package com.estapar.parking.repository

import com.estapar.parking.model.Revenue
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface RevenueRepository : JpaRepository<Revenue, Long> {
    fun findBySectorAndDate(sector: String, date: LocalDate): Revenue?
}