package ru.itis.f1app.feature.races.impl.data.mapper

import java.time.LocalDate

private const val DATE_LENGTH = 10
private const val MONTH_ABBR_LENGTH = 3

fun parseDateToDayMonth(dateString: String): Pair<String, String> {
    return try {
        val cleanDate = if (dateString.length >= DATE_LENGTH) dateString.take(DATE_LENGTH) else dateString
        val date = LocalDate.parse(cleanDate)

        val day = date.dayOfMonth.toString()
        val month = date.month.name.take(MONTH_ABBR_LENGTH)

        day to month
    } catch (_: Exception) {
        "?" to "?"
    }
}
