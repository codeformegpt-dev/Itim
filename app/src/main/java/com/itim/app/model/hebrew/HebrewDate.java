package com.itim.app.model.hebrew;

import java.time.LocalDate;

import androidx.annotation.NonNull;
import android.icu.util.Calendar;
import android.icu.util.HebrewCalendar;


public final class HebrewDate {

    public final int year;
    public final int month; //1-14, 13= אדר ת 14= אדר ב, מתחיל מניסן.
    public final int day; //1-30

    public HebrewDate(int year, int month, int day) {
        if (year < 1)
            throw new IllegalArgumentException("year must be >= 1, got " + year);
            
        if (month < 1 || month > 14)
            throw new IllegalArgumentException("month must be 1-14, got " + month);

        if (day < 1 || day > 30)
            throw new IllegalArgumentException("day must be 1-30, got " + day);

        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }
    public int getMonth() {
        return month;
    }
    public int getDay() {
        return day; 
    }

    public boolean isLeapYear() {
        return isLeapYear(year);
    }

    public static boolean isLeapYear(int year) {
        int mod19 = year % 19;
        return mod19 == 0 || mod19 == 3 || mod19 == 6 || mod19 == 8 || mod19 == 11 || mod19 == 14 || mod19 == 17;
    }

    public static HebrewDate fromGregorian(@NonNull LocalDate gregorianDate) {
    HebrewCalendar cal = new HebrewCalendar();
    cal.clear();
    cal.set(Calendar.JULIAN_DAY, (int) gregorianDate.toEpochDay() + JULIAN_DAY_OF_EPOCH);

    int year = cal.get(Calendar.YEAR);
    int icuMonth = cal.get(Calendar.MONTH);
    int day = cal.get(Calendar.DAY_OF_MONTH);

    return new HebrewDate(year, fromIcuMonth(icuMonth, isLeapYear(year)), day);
}

// חודשי ICU: TISHRI=0 ... SHEVAT=4, ADAR_1=5, ADAR=6, NISAN=7 ... ELUL=12
private static int fromIcuMonth(int icuMonth, boolean leapYear) {
    switch (icuMonth) {
        case HebrewCalendar.ADAR_1:
            return 13;                       // אדר א' (רק בשנה מעוברת)
        case HebrewCalendar.ADAR:
            return leapYear ? 14 : 12;       // אדר ב' בשנה מעוברת, אדר רגיל אחרת
        default:
            return icuMonth >= HebrewCalendar.NISAN
                    ? icuMonth - 6           // ניסן..אלול -> 1..6
                    : icuMonth + 7;          // תשרי..שבט -> 7..11
    }
}

}
