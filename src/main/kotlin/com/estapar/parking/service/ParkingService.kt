package com.estapar.parking.service

import com.estapar.parking.repository.GarageRepository
import com.estapar.parking.repository.RevenueRepository
import com.estapar.parking.repository.SpotRepository
import com.estapar.parking.repository.VehicleRepository
import com.estapar.parking.util.calculatePrice
import com.estapar.parking.dto.WebhookEventDTO
import com.estapar.parking.model.Revenue
import com.estapar.parking.model.Vehicle
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime
import kotlin.math.ceil

@Service
class ParkingService(
    private val spotRepo: SpotRepository,
    private val vehicleRepo: VehicleRepository,
    private val garageRepo: GarageRepository,
    private val revenueRepo: RevenueRepository
) {

    fun processEvent(dto: WebhookEventDTO) {
        when (dto.event_type) {

            "ENTRY" -> handleEntry(
                Vehicle(
                    licensePlate = dto.license_plate,
                    sector = dto.sector,
                    entryTime = dto.entry_time
                )
            )

            "EXIT" -> handleExit(
                Vehicle(
                    licensePlate = dto.license_plate,
                    sector = "A",
                    entryTime = dto.entry_time
                ),
                dto.exit_time!!
            )

            "PARKED" -> {
                println("Carro estacionado: ${dto.license_plate}")
            }
        }
    }

    @Transactional
    fun handleEntry(vehicle: Vehicle) {
        val garage = garageRepo.findById(vehicle.sector).orElseThrow()
        val freeSpots = spotRepo.findBySectorAndOccupied(vehicle.sector, false)

        if (freeSpots.isEmpty()) {
            throw RuntimeException("Setor cheio")
        }

        val occupancyRate = 1 - freeSpots.size.toDouble() / garage.maxCapacity
        val price = calculatePrice(garage.basePrice, occupancyRate)

        val spot = freeSpots.first()
        spot.occupied = true
        spotRepo.save(spot)

        vehicle.price = price
        vehicleRepo.save(vehicle)
    }

    @Transactional
    fun handleExit(vehicle: Vehicle, exitTime: LocalDateTime) {
        val spot = spotRepo.findBySectorAndOccupied(vehicle.sector, true)
            .firstOrNull() ?: return

        spot.occupied = false
        spotRepo.save(spot)

        val entryTime = vehicle.entryTime ?: return
        val durationMinutes = Duration.between(entryTime, exitTime).toMinutes()

        val price = if (durationMinutes <= 30) {
            0.0
        } else {
            ceil(durationMinutes / 60.0) * (vehicle.price ?: 0.0)
        }

        vehicle.exitTime = exitTime
        vehicle.price = price
        vehicleRepo.save(vehicle)

        val date = exitTime.toLocalDate()

        val revenue = revenueRepo.findBySectorAndDate(vehicle.sector, date)
            ?: Revenue(
                sector = vehicle.sector,
                date = date,
                amount = 0.0
            )

        revenue.amount += price
        revenueRepo.save(revenue)
    }
}