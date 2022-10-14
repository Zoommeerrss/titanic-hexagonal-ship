package com.titanic.hexagonal.domain.usecase.message.business

enum class BusinessMessageEnum(val message: String) {

    INVALID_PARAM("Invalid Parameter"),
    ITEM_NOT_FOUND("Item was not found"),
    NO_DATA_FOUND("No data found"),
}