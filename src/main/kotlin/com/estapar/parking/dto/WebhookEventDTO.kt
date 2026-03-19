package com.estapar.parking.dto

import java.time.LocalDateTime

data class WebhookEventDTO(
    val license_plate: String,
    val event_type: String,
    val entry_time: LocalDateTime? = null,
    val exit_time: LocalDateTime? = null,
    val lat: Double? = null,
    val lng: Double? = null
)