package com.titanic.hexagonal.domain.usecase.passenger

import com.titanic.hexagonal.domain.service.port.PassengerService
import com.titanic.hexagonal.domain.usecase.message.business.BusinessMessageEnum
import com.titanic.hexagonal.domain.usecase.UseCase
import com.titanic.hexagonal.domain.usecase.exception.NotFoundException
import com.titanic.hexagonal.domain.usecase.exception.ValidationException

class GetExistsPassengerByTicketIdUseCase(
    private val passengerService: PassengerService
): UseCase<Long, Boolean> {

    override fun execute(input: Long): Boolean {
        if(input <= 0)
            throw ValidationException(BusinessMessageEnum.INVALID_PARAM.message)

        val result = passengerService.existsByTicketId(input)

        if (result == null)
            throw NotFoundException(BusinessMessageEnum.NO_DATA_FOUND.message)
        return result
    }
}