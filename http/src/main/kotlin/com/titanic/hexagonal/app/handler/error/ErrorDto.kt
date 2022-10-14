package com.titanic.hexagonal.app.handler.error

data class ErrorDto(
    val errorCode: ErrorCodeDto?,
    val message: String?
)

enum class ErrorCodeDto {
    NOT_FOUND,
    VALIDATION_ERROR
}