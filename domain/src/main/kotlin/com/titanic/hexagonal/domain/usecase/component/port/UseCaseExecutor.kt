package com.titanic.hexagonal.domain.usecase.component.port

import com.titanic.hexagonal.domain.usecase.UseCase
import java.util.concurrent.CompletionStage

interface UseCaseExecutor {

    // for find something by any request param and returns DTO to response
    operator fun <RequestDto, ResponseDto, Request, Response> invoke(
        useCase: UseCase<Request, Response>,
        requestDto: RequestDto?,
        requestConverter: (RequestDto?) -> Request,
        responseConverter: (Response) -> ResponseDto
    ): CompletionStage<ResponseDto>

    // for listing data
    operator fun <RequestDto, Request> invoke(
        useCase: UseCase<Request, Unit>,
        requestDto: RequestDto?,
        requestConverter: (RequestDto?) -> Request
    ) =
        invoke(useCase, requestDto, requestConverter, { })

    // for saving/sending data
    operator fun <ResponseDto, Response> invoke(
        useCase: UseCase<Unit, Response>,
        responseConverter: (Response) -> ResponseDto
    ) =
        invoke(useCase, Unit, { }, responseConverter)

    //
    operator fun invoke(useCase: UseCase<Unit, Unit>) =
        invoke(useCase, Unit, { })

}