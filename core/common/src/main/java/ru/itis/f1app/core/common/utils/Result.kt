package ru.itis.f1app.core.common.utils

sealed interface Result<out T> {

    data class Success<T>(val data: T) : Result<T>

    data class Error(val exception: Throwable) : Result<Nothing>

    data object Loading : Result<Nothing>
}

inline fun <T, R> Result<T>.map(transform: (T) -> R): Result<R> {
    return when (this) {
        is Result.Success -> Result.Success(transform(data))
        is Result.Error -> this
        is Result.Loading -> Result.Loading
    }
}
