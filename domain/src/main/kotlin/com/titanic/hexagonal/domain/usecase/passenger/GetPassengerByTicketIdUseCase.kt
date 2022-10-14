package com.titanic.hexagonal.domain.usecase.passenger

import com.titanic.hexagonal.core.domain.entity.PassengerDto
import com.titanic.hexagonal.domain.service.port.PassengerService
import com.titanic.hexagonal.domain.usecase.message.business.BusinessMessageEnum
import com.titanic.hexagonal.domain.usecase.UseCase
import com.titanic.hexagonal.domain.usecase.exception.NotFoundException
import com.titanic.hexagonal.domain.usecase.exception.ValidationException

class GetPassengerByTicketIdUseCase(
    private val passengerService: PassengerService
): UseCase<Long, PassengerDto> {

    override fun execute(input: Long): PassengerDto {
        val result: PassengerDto = PassengerDto(0, null, 0, 0)

        try {
            if (input <= 0)
                throw ValidationException(BusinessMessageEnum.INVALID_PARAM.message)

            val result = passengerService.findByTicketId(input)

            if (result == null)
                throw NotFoundException(BusinessMessageEnum.NO_DATA_FOUND.message)
            return result
        }
        catch (e: Exception) {
            throw NotFoundException(BusinessMessageEnum.NO_DATA_FOUND.message)
        }

    }
}