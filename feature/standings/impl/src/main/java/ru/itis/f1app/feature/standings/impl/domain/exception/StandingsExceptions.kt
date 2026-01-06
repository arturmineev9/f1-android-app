package ru.itis.f1app.feature.standings.impl.domain.exception

sealed class StandingsExceptions : RuntimeException() {
    class NetworkConnection(override val cause: Throwable) : StandingsExceptions()
    class ServerError(val code: Int, override val message: String?) : StandingsExceptions()
    class NoDataAvailable : StandingsExceptions()
    class Unknown(override val cause: Throwable) : StandingsExceptions()
}
