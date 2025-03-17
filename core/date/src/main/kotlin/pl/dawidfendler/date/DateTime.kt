package pl.dawidfendler.date

import java.time.OffsetDateTime

interface DateTime {

    fun getCurrentDate(): OffsetDateTime
    fun convertDateToIsoLocalDateFormat(date: OffsetDateTime): String
    fun isToday(dateTime: OffsetDateTime): Boolean
    fun convertStringToOffsetDateTimeIsoFormat(date: String): OffsetDateTime
    fun convertDateToDayMonthYearHourMinuteFormat(date: OffsetDateTime): String
}
