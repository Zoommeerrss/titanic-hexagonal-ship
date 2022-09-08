package com.titanic.hexagonal.app.component

import com.titanic.hexagonal.app.component.port.PassengerAdapterService
import com.titanic.hexagonal.app.response.PassengerResponse
import org.springframework.stereotype.Component

@Component
class PassengerAdapterServiceImpl: PassengerAdapterService {

    override fun findByTicketId(ticketId: Long): PassengerResponse {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<PassengerResponse> {
        TODO("Not yet implemented")
    }
}