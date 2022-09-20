package com.titanic.hexagonal.datastore.component

import com.titanic.hexagonal.core.datastore.entity.Passenger
import com.titanic.hexagonal.datastore.component.port.PassengerDataService
import com.titanic.hexagonal.datastore.dataprovider.repository.PassengerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PassengerDataServiceImpl: PassengerDataService {

    @Autowired
    lateinit var repository: PassengerRepository

    override fun findByTicketId(ticketId: Long): Passenger {
        return repository.findByTicketId(ticketId)
    }

    override fun existsByTicketId(ticketId: Long): Boolean {
        return repository.existsByTicketId(ticketId)
    }

    override fun findAll(): List<Passenger> {
        return repository.findAll()
    }
}