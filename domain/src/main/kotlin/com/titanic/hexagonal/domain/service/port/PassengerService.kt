package com.titanic.hexagonal.domain.service.port

import com.titanic.hexagonal.core.domain.entity.PassengerDto

interface PassengerService {

    fun findByTicketId(ticketId: Long): PassengerDto
    fun existsByTicketId(ticketId: Long): Boolean
    fun findAll(): List<PassengerDto>
}