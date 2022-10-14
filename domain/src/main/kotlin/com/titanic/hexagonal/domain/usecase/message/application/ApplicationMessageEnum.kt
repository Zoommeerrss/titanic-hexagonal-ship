package com.titanic.hexagonal.domain.usecase.message.application

enum class ApplicationMessageEnum(val message: String) {

    INVALID_PARAM("Invalid Parameter"),
    ITEM_NOT_FOUND("Item was not found"),
    RESOURCE_NOT_FOUND("Resource not found");

}