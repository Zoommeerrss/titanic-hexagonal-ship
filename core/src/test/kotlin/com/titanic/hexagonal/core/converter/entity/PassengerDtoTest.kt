package com.titanic.hexagonal.core.converter.entity

import com.titanic.hexagonal.core.domain.entity.PassengerDto
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class PassengerDtoTest {

    @Test
    fun `valida DTO`() {

        val bo = PassengerDto(
            1,
            "test",
            1,
            1
        )

        assertEquals(1, bo.ticketId)
        assertEquals("test", bo.name)
        assertEquals(1, bo.classDesc)
        assertEquals(1, bo.floorLevel)
    }
}