package com.titanic.hexagonal.app.configuration

import com.titanic.hexagonal.domain.service.port.PassengerService
import com.titanic.hexagonal.domain.usecase.component.UseCaseExecutorImpl
import com.titanic.hexagonal.domain.usecase.passenger.GetExistsPassengerByTicketIdUseCase
import com.titanic.hexagonal.domain.usecase.passenger.GetPassengerByTicketIdUseCase
import com.titanic.hexagonal.domain.usecase.passenger.GetPassengersUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ModuleConfiguration {

    @Bean
    fun useCaseExecutor() = UseCaseExecutorImpl()

    @Bean
    fun getExistsPassengerByTicketIdUseCase(service: PassengerService) = GetExistsPassengerByTicketIdUseCase(service)

    @Bean
    fun getPassengerByTicketIdUseCase(service: PassengerService) = GetPassengerByTicketIdUseCase(service)

    @Bean
    fun getPassengersUseCase(service: PassengerService) = GetPassengersUseCase(service)

}