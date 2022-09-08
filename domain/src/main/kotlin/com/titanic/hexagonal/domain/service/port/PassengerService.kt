package com.titanic.hexagonal.domain.service.port

import com.titanic.hexagonal.domain.entity.PassengerDto

interface PassengerService {

    fun findByTicketId(ticketId: Long): PassengerDto
    fun findAll(): List<PassengerDto>
}