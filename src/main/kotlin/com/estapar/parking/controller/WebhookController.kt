
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
    fun receiveEvent(@RequestBody dto: WebhookEventDTO) {
        parkingService.processEvent(dto)
    }
    @GetMapping("test")
    fun teste(): String {
        return "ok"
    }
}