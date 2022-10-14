package com.titanic.hexagonal.domain.usecase.component

import com.titanic.hexagonal.domain.usecase.UseCase
import com.titanic.hexagonal.domain.usecase.component.port.UseCaseExecutor
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

class UseCaseExecutorImpl: UseCaseExecutor {

    override operator fun <RequestDto, ResponseDto, Request, Response> invoke(
        useCase: UseCase<Request, Response>,
        requestDto: RequestDto?,
        requestConverter: (RequestDto?) -> Request,
        responseConverter: (Response) -> ResponseDto
    ): CompletionStage<ResponseDto> =
        CompletableFuture
            .supplyAsync { requestConverter(requestDto) }
            .thenApplyAsync { useCase.execute(it) }
            .thenApplyAsync { responseConverter(it) }
}