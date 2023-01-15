package com.jgbravo.translapptor.core.util

sealed class Resource<T>(
    val data: T? = null,
    val throwable: Throwable? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(throwable: Throwable) : Resource<T>(null, throwable)
}