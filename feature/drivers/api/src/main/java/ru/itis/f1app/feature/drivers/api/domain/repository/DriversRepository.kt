package ru.itis.f1app.feature.drivers.api.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.itis.f1app.feature.drivers.api.domain.model.DriverDetails

interface DriversRepository {
    fun getDriverDetails(year: String, driverId: String): Flow<DriverDetails>
}
