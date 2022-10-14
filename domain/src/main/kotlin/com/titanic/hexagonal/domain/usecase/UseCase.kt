package com.titanic.hexagonal.domain.usecase

interface UseCase<in Request, out Response> {
    fun execute(request: Request): Response
}