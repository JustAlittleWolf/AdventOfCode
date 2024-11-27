package me.wolfii.automation

import java.time.OffsetDateTime
import java.time.ZoneId
import java.util.*

data class Day(val day: Int, val year: Int = CURRENT_YEAR) {
    companion object {
        private const val MIN_DAY: Int = 1
        private const val MAX_DAY: Int = 25
        private const val MIN_YEAR: Int = 2015

        private val CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR)

        private const val DAY_MINUTES_OFFSET = 15L
        val CURRENT = Day(
            OffsetDateTime.now(ZoneId.of("UTC-5")).plusMinutes(DAY_MINUTES_OFFSET)
                .dayOfMonth
                .coerceAtMost(MAX_DAY)
        )
    }

    init {
        if (day < MIN_DAY || day > MAX_DAY) throw IllegalArgumentException("Day must be between 1 and 25, found $day")
        if (year < MIN_YEAR) throw IllegalArgumentException("Year must be greater than 2015, found $year")
    }

    override fun toString() = "$year-$day"
}
