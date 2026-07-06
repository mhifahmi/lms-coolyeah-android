package com.mhifahmi.lmscoolyeah.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateFormatter {

    private val inputFormatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd")

    private val outputFormatter =
        DateTimeFormatter.ofPattern(
            "dd MMM yyyy",
            Locale("id", "ID")
        )

    fun formatDate(date: String): String {

        return try {

            LocalDate.parse(
                date,
                inputFormatter
            ).format(outputFormatter)

        } catch (_: Exception) {

            date

        }

    }

}