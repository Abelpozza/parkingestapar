package com.estapar.parking.controller

import com.estapar.parking.dto.RevenueRequestDTO
import com.estapar.parking.dto.RevenueResponseDTO
import com.estapar.parking.repository.RevenueRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.LocalDateTime

@RestController
@RequestMapping("/revenue")
class RevenueController(
    private val revenueRepo: RevenueRepository) {

    @GetMapping
    fun getRevenue(
        @RequestParam date: String,
        @RequestParam sector: String):
            RevenueResponseDTO {
            val parsedDate = LocalDate.parse(date)
        val revenue = revenueRepo.findBySectorAndDate(sector, parsedDate)
        return RevenueResponseDTO(
            amount = revenue?.amount?: 0.0,
            currency = "BRL",
            timestamp = LocalDateTime.now().toString()
        )
        }
}
