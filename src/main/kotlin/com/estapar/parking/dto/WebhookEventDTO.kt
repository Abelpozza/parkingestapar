package com.estapar.parking.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class WebhookEventDTO(
    val license_plate: String,
    val event_type: String,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    val entry_time: LocalDateTime? = null,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    val exit_time: LocalDateTime? = null,
    val lat: Double? = null,
    val lng: Double? = null
)