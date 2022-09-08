package com.titanic.hexagonal.app.component.port

import com.titanic.hexagonal.app.response.PassengerResponse

interface PassengerAdapterService {

    fun findByTicketId(ticketId: Long): PassengerResponse
    fun findAll(): List<PassengerResponse>
}