package com.titanic.hexagonal.core.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties (
    ignoreUnknown = true
)
@JsonInclude(
    JsonInclude.Include.NON_NULL
)
data class PassengerDto(

    @get:JsonProperty("ticketId")
    var ticketId: Long?,

    @get:JsonProperty("name")
    var name: String?,

    @get:JsonProperty("classDesc")
    var classDesc: Long?,

    @get:JsonProperty("floorLevel")
    var floorLevel: Long?
)
