package ch.heigvd.daa_labo3

import androidx.room.TypeConverter
import java.util.*

/**
 * Converter for Calendar class
 *
 * @author Nicolas Crausaz
 * @author Lazar Pavicevic
 * @author Maxime Scharwath
 */
class CalendarConverter {
    @TypeConverter
    fun toCalendar(dateLong: Long): Calendar =
        Calendar.getInstance().apply {
            time = Date(dateLong)
        }

    @TypeConverter
    fun fromCalendar(date: Calendar): Long = date.time.time
}