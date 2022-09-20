package com.titanic.hexagonal.domain.entity

import com.titanic.hexagonal.core.domain.entity.PassengerDto
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class PassengerDtoTest {

    @Test
    fun `deve validar os campos do DTO`() {

        val passengerBo = PassengerDto(
            ticketId = 1L,
            name = "test",
            classDesc = 1L,
            floorLevel = 1L
        )

        assertEquals(1, passengerBo.floorLevel)
        assertEquals("test", passengerBo.name)
        assertEquals(1L, passengerBo.classDesc)
        assertEquals(1L, passengerBo.floorLevel)
    }
}