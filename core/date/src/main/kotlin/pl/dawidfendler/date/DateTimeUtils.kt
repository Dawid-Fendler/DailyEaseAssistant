package pl.dawidfendler.date

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class DateTimeUtils @Inject constructor(): DateTime {

    override fun getCurrentDate(): OffsetDateTime = OffsetDateTime.now()
    override fun convertDateToIsoLocalDateFormat(date: OffsetDateTime): String {
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    override fun isToday(dateTime: OffsetDateTime): Boolean {
        val today = convertDateToIsoLocalDateFormat(getCurrentDate())
        val dateToCheck = convertDateToIsoLocalDateFormat(dateTime)
        return today == dateToCheck
    }

}