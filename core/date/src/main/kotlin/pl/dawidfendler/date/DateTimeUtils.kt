package pl.dawidfendler.date

import pl.dawidfendler.util.Constants.EUROPE_OFFSET
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class DateTimeUtils @Inject constructor() : DateTime {

    override fun getCurrentDate(): OffsetDateTime = OffsetDateTime.now()
    override fun convertDateToIsoLocalDateFormat(date: OffsetDateTime): String {
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    override fun isToday(dateTime: OffsetDateTime): Boolean {
        val today = convertDateToIsoLocalDateFormat(getCurrentDate())
        val dateToCheck = convertDateToIsoLocalDateFormat(dateTime)
        return today == dateToCheck
    }

    override fun convertStringToOffsetDateTimeIsoFormat(date: String): OffsetDateTime {
        val localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
        val zoneId = ZoneId.of(EUROPE_OFFSET)
        return localDate.atStartOfDay(zoneId).toOffsetDateTime()
    }

    override fun convertDateToDayMonthYearHourMinuteFormat(date: OffsetDateTime): String {
        return date.format(DateTimeFormatter.ofPattern(DD_MM_YYYY_HH_MM_DOT))
    }

    companion object {
        private const val DD_MM_YYYY_HH_MM_DOT = "dd.MM.yyyy HH:mm"
    }
}
