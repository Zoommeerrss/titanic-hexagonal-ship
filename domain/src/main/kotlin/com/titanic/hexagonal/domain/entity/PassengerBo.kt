package com.titanic.hexagonal.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties (
    ignoreUnknown = true
)
@JsonInclude(
    JsonInclude.Include.NON_NULL
)
data class PassengerBo(

    @get:JsonProperty("ticketId")
    val ticketId: Long,

    @get:JsonProperty("name")
    val name: String,

    @get:JsonProperty("classDesc")
    val classDesc: Long,

    @get:JsonProperty("floorLevel")
    val floorLevel: Long
)
