package com.titanic.hexagonal.core.datastore.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.GenericGenerator
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "Passenger")
@JsonIgnoreProperties(
    ignoreUnknown = true
)
data class Passenger(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "ticketId", insertable = false, updatable = false, nullable = false)
    val ticketId: Long,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "classDesc", nullable = false)
    val classDesc: Long,

    @Column(name = "floorLevel", nullable = false)
    val floorLevel: Long
): Serializable
