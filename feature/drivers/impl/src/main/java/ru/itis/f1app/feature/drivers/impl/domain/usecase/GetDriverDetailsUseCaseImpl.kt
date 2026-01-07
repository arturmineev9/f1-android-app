package ru.itis.f1app.feature.drivers.impl.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.itis.f1app.feature.drivers.api.domain.model.DriverDetails
import ru.itis.f1app.feature.drivers.api.domain.repository.DriversRepository
import ru.itis.f1app.feature.drivers.api.domain.usecase.GetDriverDetailsUseCase
import javax.inject.Inject

class GetDriverDetailsUseCaseImpl @Inject constructor(
    private val repository: DriversRepository
) : GetDriverDetailsUseCase {

    override operator fun invoke(year: String, driverId: String): Flow<DriverDetails> {
        return repository.getDriverDetails(year, driverId)
    }
}
