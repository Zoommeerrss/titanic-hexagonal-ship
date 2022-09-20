package com.titanic.hexagonal.datastore.dataprovider.repository

import com.titanic.hexagonal.core.datastore.entity.Passenger
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PassengerRepository: JpaRepository<Passenger, Long> {

    fun findByTicketId(ticketId: Long): Passenger
    fun existsByTicketId(ticketId: Long): Boolean
}