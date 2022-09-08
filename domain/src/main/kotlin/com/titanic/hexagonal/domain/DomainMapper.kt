package com.titanic.hexagonal.domain

import com.titanic.hexagonal.datastore.dataprovider.entity.Passenger
import com.titanic.hexagonal.domain.entity.PassengerBo
import com.titanic.hexagonal.domain.entity.PassengerDto
import com.titanic.hexagonal.domain.entity.PassengerResponse


fun PassengerDto.toEntity(): Passenger = Passenger(
    ticketId = this.ticketId,
    name = this.name,
    classDesc = this.classDesc,
    floorLevel = this.floorLevel
)

fun Passenger.toDto(): PassengerDto = PassengerDto(
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

fun Passenger.toBo(): PassengerBo = PassengerBo(
    ticketId = this.ticketId,
    name = this.name,
    classDesc = this.classDesc,
    floorLevel = this.floorLevel
)