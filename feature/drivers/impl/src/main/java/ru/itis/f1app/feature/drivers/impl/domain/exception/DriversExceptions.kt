package ru.itis.f1app.feature.drivers.impl.domain.exception

sealed class DriversExceptions(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause) {
    class NetworkConnection(cause: Throwable) : DriversExceptions(cause = cause)
    class ServerError(val code: Int, message: String?) : DriversExceptions(message)
    class NoDataAvailable : DriversExceptions()
    class Unknown(cause: Throwable) : DriversExceptions(cause = cause)
}
