package com.jgbravo.translapptor.translate.domain

enum class TranslateError {
    SERVICE_UNAVAILABLE,
    CLIENT_ERROR,
    SERVER_ERROR,
    UNKNOWN_ERROR
}

class TranslateException(val error: TranslateError) : Exception() {
    override val message: String = "An error occurred when translating: $error"
}