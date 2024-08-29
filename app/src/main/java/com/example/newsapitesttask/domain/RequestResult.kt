package com.example.newsapitesttask.domain

sealed class RequestResult<out T, out E> {

    data class Success<T>(
        val value: T,
    ) : RequestResult<T, Nothing>()

    data object Loading : RequestResult<Nothing, Nothing>()

    data class Error<E>(
        val error: E,
    ) : RequestResult<Nothing, E>()
}
