package com.titanic.hexagonal.domain.service


import com.titanic.hexagonal.datastore.component.port.PassengerDataService
import com.titanic.hexagonal.datastore.dataprovider.entity.Passenger
import com.titanic.hexagonal.domain.entity.PassengerDto
import com.titanic.hexagonal.domain.service.port.PassengerService
import com.titanic.hexagonal.domain.toDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class PassengerServiceImpl: PassengerService {

    @Autowired
    lateinit var passengerDataService: PassengerDataService

    override fun findByTicketId(ticketId: Long): PassengerDto {
        return passengerDataService.findByTicketId(ticketId).toDto()
    }

    override fun findAll(): List<PassengerDto> {
        return passengerDataService.findAll()
            .stream()
            .map(Passenger::toDto)
            .collect(Collectors.toList())
    }

}