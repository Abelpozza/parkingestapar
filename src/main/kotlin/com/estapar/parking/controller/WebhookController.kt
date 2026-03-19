
package com.estapar.parking.controller

import com.estapar.parking.service.ParkingService
import com.estapar.parking.dto.WebhookEventDTO
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/webhook")
class WebhookController(
    private val parkingService: ParkingService
) {

    @PostMapping
    fun receiveEvent(@RequestBody dto: WebhookEventDTO): String {
        return try {
            println("DTO recebido: $dto")
            parkingService.processEvent(dto)
            return "evento recebido!"
        } catch (e: Exception) {
            e.printStackTrace()
            "ERRO: ${e.message}"
        }
        @GetMapping("/test")
        fun teste(): String {
            return "ok"
        }
    }
}

