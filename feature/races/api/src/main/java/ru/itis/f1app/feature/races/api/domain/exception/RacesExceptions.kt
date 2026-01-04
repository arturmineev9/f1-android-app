package ru.itis.f1app.feature.races.api.domain.exception

sealed class RacesExceptions(
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception(message, cause) {

    class NetworkConnection(cause: Throwable? = null) :
        RacesExceptions("Network connection error", cause)

    class ServerError(val code: Int, message: String? = null) :
        RacesExceptions("Server error $code: $message")

    class NoDataAvailable : RacesExceptions("No races found for this year")

    class Unknown(cause: Throwable? = null) : RacesExceptions("Unknown error", cause)
}
