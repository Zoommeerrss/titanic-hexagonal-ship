package com.titanic.hexagonal.app.entrypoint

import com.titanic.hexagonal.domain.service.port.PassengerService
import com.titanic.hexagonal.core.converter.toResponse
import com.titanic.hexagonal.core.domain.entity.PassengerDto
import com.titanic.hexagonal.core.http.entity.response.PassengerResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/passengers")
@Validated
class WelcomeAboard(val passangerService: PassengerService) {

    @Operation(summary = "Welcome Aboard API", description = "Returns 200 if successful")
    @GetMapping
    fun welcome(): ResponseEntity<String> {
        return ResponseEntity("Welcome to Titanic Hexagonal Ship", HttpStatus.OK)
    }

    @Operation(summary = "Lista os passageiros", description = "Returns 200 if successful")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful Operation"),
            ApiResponse(responseCode = "403", description = "Such a car does not exist"),
            ApiResponse(responseCode = "404", description = "Bad Request"),
            ApiResponse(responseCode = "500", description = "Internal Server Error"),
        ]
    )
    @GetMapping(value = ["/findAllAbord"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllAbord(): ResponseEntity<List<PassengerResponse>> {

        val response: List<PassengerResponse> = passangerService.findAll()
            .stream()
            .map(PassengerDto::toResponse)
            .collect(Collectors.toList())
            .toList()

        return ResponseEntity(response, HttpStatus.OK)
    }
}