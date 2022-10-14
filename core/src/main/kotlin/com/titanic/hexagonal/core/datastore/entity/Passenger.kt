package com.titanic.hexagonal.core.datastore.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "Passenger")
@JsonIgnoreProperties(
    ignoreUnknown = true
)
data class Passenger(

    @Id
    @Column(name = "ticketId", insertable = false, updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val ticketId: Long?,

    @Column(name = "name", nullable = false)
    val name: String?,

    @Column(name = "classDesc", nullable = false)
    val classDesc: Long?,

    @Column(name = "floorLevel", nullable = false)
    val floorLevel: Long?
): Serializable
