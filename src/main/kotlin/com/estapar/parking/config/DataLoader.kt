package com.estapar.parking.config

import com.estapar.parking.dto.GarageResponseDTO
import com.estapar.parking.model.Garage
import com.estapar.parking.model.Spot
import com.estapar.parking.repository.GarageRepository
import com.estapar.parking.repository.SpotRepository
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate


@Component
class DataLoader(
    private val garageRepo: GarageRepository,
    private val spotRepo: SpotRepository
) {

    private val restTemplate = RestTemplate()

    @PostConstruct
    fun loadGarageData() {
        try {
        val url = "http://localhost:3000/garage"
        val response = restTemplate.getForObject(url, GarageResponseDTO::class.java)

        response?.let {

            println("Carregando dados")

            it.garage.forEach { g ->
                val garage = Garage(
                    sector = g.sector,
                    basePrice = g.basePrice,
                    maxCapacity = g.max_capacity
                )
                garageRepo.save(garage)
            }
            it.spots.forEach { s ->
                val spot = Spot(
                    id = s.id,
                    sector = s.sector,
                    lat = s.lat,
                    lng = s.lng,
                    occupied = false
                )
                spotRepo.save(spot)
            }
            println("Dados carregados com sucesso!")
        }
        } catch (e: Exception) {
            println("Não Carregou os dados: ${e.message}")
        }

    }
}