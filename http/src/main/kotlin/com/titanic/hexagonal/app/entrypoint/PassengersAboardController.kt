package com.titanic.hexagonal.app.entrypoint

import com.titanic.hexagonal.core.converter.toResponse
import com.titanic.hexagonal.core.domain.entity.PassengerDto
import com.titanic.hexagonal.core.http.entity.response.PassengerResponse
import com.titanic.hexagonal.domain.usecase.passenger.GetPassengersUseCase
import com.titanic.hexagonal.domain.usecase.component.port.UseCaseExecutor
import com.titanic.hexagonal.domain.usecase.passenger.GetExistsPassengerByTicketIdUseCase
import com.titanic.hexagonal.domain.usecase.passenger.GetPassengerByTicketIdUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.util.concurrent.CompletionStage
import java.util.stream.Collectors

@RestController
@RequestMapping("/titanic_ship/v1")
@Validated
class PassengersAboardController(
    private val useCaseExecutor: UseCaseExecutor,
    private val getExistsPassengerByTicketIdUseCase: GetExistsPassengerByTicketIdUseCase,
    private val getPassengerByTicketIdUseCase: GetPassengerByTicketIdUseCase,
    private val getPassengersUseCase: GetPassengersUseCase,
) {

    @Operation(summary = "Lista os passageiros", description = "Returns 200 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful Operation"),
            ApiResponse(responseCode = "403", description = "Such a car does not exist"),
            ApiResponse(responseCode = "404", description = "Bad Request"),
            ApiResponse(responseCode = "500", description = "Internal Server Error"),
        ]
    )
    @GetMapping(value = ["/passengers"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun passengers(): CompletionStage<ResponseEntity<List<PassengerResponse>>> {

        return useCaseExecutor(
            useCase = getPassengersUseCase,
            responseConverter = {
                ResponseEntity(it.stream().map(PassengerDto::toResponse).collect(Collectors.toList()), HttpStatus.OK)
            }
        )
    }

    @Operation(summary = "Verifica se existe o passageiro pelo TicketId", description = "Returns 200 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful Operation"),
            ApiResponse(responseCode = "403", description = "Such a car does not exist"),
            ApiResponse(responseCode = "404", description = "Bad Request"),
            ApiResponse(responseCode = "500", description = "Internal Server Error"),
        ]
    )
    @GetMapping(value = ["/passengers/existsByTicketId/{ticketId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun existsByTicketId(@PathVariable("ticketId") tickedId: Long): CompletionStage<ResponseEntity<Boolean>> {

        return useCaseExecutor(
            useCase = getExistsPassengerByTicketIdUseCase,
            requestDto = tickedId,
            requestConverter = { it!! },
            responseConverter = {
                ResponseEntity(it, HttpStatus.OK)
            }
        )
    }

    @Operation(summary = "Busca o passageiro pelo TicketId", description = "Returns 200 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful Operation"),
            ApiResponse(responseCode = "403", description = "Such a car does not exist"),
            ApiResponse(responseCode = "404", description = "Bad Request"),
            ApiResponse(responseCode = "500", description = "Internal Server Error"),
        ]
    )
    @GetMapping(value = ["/passengers/byTicketId/{ticketId}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun byTicketId(@PathVariable("ticketId") tickedId: Long): CompletionStage<ResponseEntity<PassengerResponse>> {

        return useCaseExecutor(
            useCase = getPassengerByTicketIdUseCase,
            requestDto = tickedId,
            requestConverter = { it!! },
            responseConverter = {
                ResponseEntity(it.toResponse(), HttpStatus.OK)
            }
        )
    }
}