package com.example.util

import java.util.Calendar
import java.util.GregorianCalendar

object JalaliCalendarHelper {

    /**
     * Converts a millisecond timestamp to a formatted Jalali date string (e.g., "۱۴۰۵/۰۴/۰۵" or "۵ تیر ۱۴۰۵").
     */
    fun getFormattedJalaliDate(timestamp: Long, withMonthName: Boolean = true): String {
        val calendar = GregorianCalendar()
        calendar.timeInMillis = timestamp
        
        val gYear = calendar.get(Calendar.YEAR)
        val gMonth = calendar.get(Calendar.MONTH) + 1
        val gDay = calendar.get(Calendar.DAY_OF_MONTH)

        val jalali = gregorianToJalali(gYear, gMonth, gDay)
        
        return if (withMonthName) {
            val monthName = getPersianMonthName(jalali.month)
            toPersianDigits("${jalali.day} $monthName ${jalali.year}")
        } else {
            val mStr = if (jalali.month < 10) "۰${jalali.month}" else "${jalali.month}"
            val dStr = if (jalali.day < 10) "۰${jalali.day}" else "${jalali.day}"
            toPersianDigits("${jalali.year}/$mStr/$dStr")
        }
    }

    data class JalaliDate(val year: Int, val month: Int, val day: Int)

    /**
     * Algorithmic conversion of Gregorian date to Jalali date.
     */
    fun gregorianToJalali(gYear: Int, gMonth: Int, gDay: Int): JalaliDate {
        val gDaysInMonth = intArrayOf(0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
        val jDaysInMonth = intArrayOf(0, 31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29)

        var gy = gYear - 1600
        var gm = gMonth - 1
        var gd = gDay - 1

        var gDayNo = 365 * gy + gy / 4 - gy / 100 + gy / 400
        for (i in 0 until gm) {
            gDayNo += gDaysInMonth[i + 1]
        }
        if (gm > 1 && ((gy % 4 == 0 && gy % 100 != 0) || (gy % 400 == 0))) {
            gDayNo++ // Leap year check
        }
        gDayNo += gd

        var jDayNo = gDayNo - 79

        val jNp = jDayNo / 12053
        jDayNo %= 12053

        var jy = 979 + 33 * jNp + 4 * (jDayNo / 1461)
        jDayNo %= 1461

        if (jDayNo >= 366) {
            jy += (jDayNo - 1) / 365
            jDayNo = (jDayNo - 1) % 365
        }

        var jm = 0
        for (i in 0..11) {
            val days = jDaysInMonth[i + 1]
            if (jDayNo < days) {
                jm = i + 1
                break
            }
            jDayNo -= days
        }
        val jd = jDayNo + 1

        return JalaliDate(jy, jm, jd)
    }

    fun getPersianMonthName(month: Int): String {
        return when (month) {
            1 -> "فروردین"
            2 -> "اردیبهشت"
            3 -> "خرداد"
            4 -> "تیر"
            5 -> "مرداد"
            6 -> "شهریور"
            7 -> "مهر"
            8 -> "آبان"
            9 -> "آذر"
            10 -> "دی"
            11 -> "بهمن"
            12 -> "اسفند"
            else -> ""
        }
    }

    /**
     * Converts English digits in a string to Persian digits.
     */
    fun toPersianDigits(input: String): String {
        var result = input
        val faDigits = arrayOf("۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹")
        for (i in 0..9) {
            result = result.replace(i.toString(), faDigits[i])
        }
        return result
    }
}
