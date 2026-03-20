package com.estapar.parking.service

import com.estapar.parking.dto.WebhookEventDTO
import com.estapar.parking.model.Revenue
import com.estapar.parking.model.Vehicle
import com.estapar.parking.repository.GarageRepository
import com.estapar.parking.repository.RevenueRepository
import com.estapar.parking.repository.SpotRepository
import com.estapar.parking.repository.VehicleRepository
import com.estapar.parking.util.calculatePrice
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.Duration
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
            "ENTRY" -> handleEntry(dto)
            "EXIT" -> handleExit(dto)
            "PARKED" -> handleParked(dto)
            else -> throw RuntimeException("Tipo de evento inválido: ${dto.event_type}")
        }
    }

    @Transactional
    fun handleEntry(dto: WebhookEventDTO) {
        val freeSpot = spotRepo.findByOccupied(false).firstOrNull()
            ?: throw RuntimeException("Estacionamento cheio")

        val garage = garageRepo.findById(freeSpot.sector)
            .orElseThrow { RuntimeException("Setor ${freeSpot.sector} não encontrado") }

        val freeSpotsInSector = spotRepo.findBySectorAndOccupied(freeSpot.sector, false)

        val occupancyRate = 1 - freeSpotsInSector.size.toDouble() / garage.maxCapacity
        val price = calculatePrice(garage.basePrice, occupancyRate)

        freeSpot.occupied = true
        spotRepo.save(freeSpot)

        val vehicle = Vehicle(
            licensePlate = dto.license_plate,
            sector = freeSpot.sector,
            entryTime = dto.entry_time,
            price = price
        )

        vehicleRepo.save(vehicle)
    }

    @Transactional
    fun handleExit(dto: WebhookEventDTO) {
        val vehicle = vehicleRepo.findById(dto.license_plate)
            .orElseThrow { RuntimeException("Veículo não encontrado: ${dto.license_plate}") }

        val spot = spotRepo.findBySectorAndOccupied(vehicle.sector, true)
            .firstOrNull()
            ?: throw RuntimeException("Nenhuma vaga ocupada encontrada no setor ${vehicle.sector}")

        spot.occupied = false
        spotRepo.save(spot)

        val exitTime = dto.exit_time
            ?: throw RuntimeException("exit_time é obrigatório para EXIT")

        val entryTime = vehicle.entryTime
            ?: throw RuntimeException("entry_time do veículo não encontrado")

        val durationMinutes = Duration.between(entryTime, exitTime).toMinutes()

        val finalPrice = if (durationMinutes <= 30) {
            0.0
        } else {
            ceil(durationMinutes / 60.0) * (vehicle.price ?: 0.0)
        }

        vehicle.exitTime = exitTime
        vehicle.price = finalPrice
        vehicleRepo.save(vehicle)

        val date = exitTime.toLocalDate()

        val revenue = revenueRepo.findBySectorAndDate(vehicle.sector, date)
            ?: Revenue(
                sector = vehicle.sector,
                date = date,
                amount = 0.0
            )

        revenue.amount += finalPrice
        revenueRepo.save(revenue)
    }

    fun handleParked(dto: WebhookEventDTO) {
        println("Carro estacionado: ${dto.license_plate} em lat=${dto.lat}, lng=${dto.lng}")
    }
}