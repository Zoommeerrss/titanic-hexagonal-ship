package com.titanic.hexagonal.datastore.component.port

import com.titanic.hexagonal.datastore.dataprovider.entity.Passenger

interface PassengerDataService {
    fun findByTicketId(ticketId: Long): Passenger
    fun existsByTicketId(ticketId: Long): Boolean
    fun findAll(): List<Passenger>
}