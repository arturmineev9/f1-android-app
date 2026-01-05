package ru.itis.f1app.feature.races.impl.presentation.utils

import ru.itis.f1app.feature.races.api.domain.model.Race

private const val DATE_LENGTH = 10

val Race.formattedDate: String
    get() = if (dateStart.length >= DATE_LENGTH) {
        dateStart.take(DATE_LENGTH)
    } else {
        dateStart
    }
