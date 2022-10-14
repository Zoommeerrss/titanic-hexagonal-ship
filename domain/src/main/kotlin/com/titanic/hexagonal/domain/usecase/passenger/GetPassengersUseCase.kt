package com.titanic.hexagonal.domain.usecase.passenger

import com.titanic.hexagonal.core.domain.entity.PassengerDto
import com.titanic.hexagonal.domain.service.port.PassengerService
import com.titanic.hexagonal.domain.usecase.message.business.BusinessMessageEnum
import com.titanic.hexagonal.domain.usecase.UseCase
import com.titanic.hexagonal.domain.usecase.exception.NotFoundException

class GetPassengersUseCase(
    private val passengerService: PassengerService
): UseCase<Unit, List<PassengerDto>> {

    override fun execute(input: Unit): List<PassengerDto> {
        val result = passengerService.findAll()
        if (result.isEmpty())
            throw NotFoundException(BusinessMessageEnum.NO_DATA_FOUND.message)
        return result
    }

}