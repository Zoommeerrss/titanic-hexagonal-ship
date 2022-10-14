package com.titanic.hexagonal.app.handler

import com.titanic.hexagonal.app.handler.error.ErrorCodeDto
import com.titanic.hexagonal.app.handler.error.ErrorDto
import com.titanic.hexagonal.domain.usecase.exception.NotFoundException
import com.titanic.hexagonal.domain.usecase.exception.ValidationException
import com.titanic.hexagonal.domain.usecase.message.application.ApplicationMessageEnum
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
@RestController
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(NotFoundException::class)
    fun notFound(ex: NotFoundException) =
        ResponseEntity(ErrorDto(ErrorCodeDto.NOT_FOUND, ApplicationMessageEnum.RESOURCE_NOT_FOUND.toString()), HttpStatus.NOT_FOUND)

    @ExceptionHandler(ValidationException::class)
    fun invalidParam(ex: ValidationException) =
        ResponseEntity(ErrorDto(ErrorCodeDto.VALIDATION_ERROR, ex.message), HttpStatus.BAD_REQUEST)
}