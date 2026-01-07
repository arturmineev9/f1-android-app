package ru.itis.f1app.feature.races.impl.data.mapper

import java.time.LocalDate

fun parseDateToDayMonth(dateString: String): Pair<String, String> {
    return try {
        val cleanDate = if (dateString.length >= 10) dateString.take(10) else dateString

        val date = LocalDate.parse(cleanDate)

        val day = date.dayOfMonth.toString()
        val month = date.month.name.take(3)

        day to month
    } catch (e: Exception) {
        e.printStackTrace()
        "?" to "?"
    }
}
