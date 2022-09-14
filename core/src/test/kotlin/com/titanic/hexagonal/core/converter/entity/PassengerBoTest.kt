package com.titanic.hexagonal.core.converter.entity

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class PassengerBoTest {

    @Test
    fun `valida BO`() {

        val bo = PassengerBo(
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