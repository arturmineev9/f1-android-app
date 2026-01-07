package ru.itis.f1app.feature.drivers.api.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.itis.f1app.feature.drivers.api.domain.model.DriverDetails

interface GetDriverDetailsUseCase {
    operator fun invoke(year: String, driverId: String): Flow<DriverDetails>
}
