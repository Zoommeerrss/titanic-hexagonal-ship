package com.titanic.hexagonal.domain.entity

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class PassengerBoTest {

    @Test
    fun `deve validar os campos do BO`() {

        val passengerBo = PassengerBo(
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