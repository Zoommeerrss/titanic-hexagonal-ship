package com.titanic.hexagonal.core.converter

import com.titanic.hexagonal.core.domain.entity.PassengerDto
import com.titanic.hexagonal.core.http.entity.request.PassengerRequest
import com.titanic.hexagonal.core.http.entity.response.PassengerResponse

fun PassengerRequest.toDto(): PassengerDto = PassengerDto(
    ticketId = this.ticketId,
    name = this.name,
    classDesc = this.classDesc,
    floorLevel = this.floorLevel
)

fun PassengerDto.toResponse(): PassengerResponse = PassengerResponse(
    ticketId = this.ticketId,
    name = this.name,
    classDesc = this.classDesc,
    floorLevel = this.floorLevel
)
