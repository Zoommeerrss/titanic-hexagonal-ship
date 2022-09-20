package com.titanic.hexagonal.core.converter

import com.titanic.hexagonal.core.datastore.entity.Passenger
import com.titanic.hexagonal.core.domain.entity.PassengerDto

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
