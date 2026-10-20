package com.itim.app.model.hebrew;

import java.time.LocalDate;
import java.util.Objects;

import androidx.annotation.NonNull;
import android.icu.util.Calendar;
import android.icu.util.HebrewCalendar;
import android.icu.util.TimeZone;


public final class HebrewDate {

    /** מספר היום היוליאני של 1970-01-01, שהוא יום 0 ב-java.time*/
    private static final int JULIAN_DAY_OF_EPOCH = 2440588;




    public final int year;
    public final int month;
    public final int day;

    public HebrewDate(int year, int month, int day) {
        if (year < 1)
            throw new IllegalArgumentException("year must be >= 1, got " + year);
        if (month < 1 || month > 14)
            throw new IllegalArgumentException("month must be 1-14, got " + month);

        boolean isLeapYear =isLeapYear(year);
        if (isLeapYear ? month == 12 : month >= 13)
            throw new IllegalArgumentException("month " + month + " invalid for " + (isLeapYear ? "leap year" : "non leap year") + ":" + year);

        int lengthOfMonth = lengthOfMonth(year, month);
        if (day < 1 || day > lengthOfMonth)
            throw new IllegalArgumentException("day must be 1-" + lengthOfMonth + ", got " + day);

        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }
    public int getMonth() { return month;}
    public int getDay() { return day; }

    public boolean isLeapYear() {
        return isLeapYear(year);
    }

    public static boolean isLeapYear(int year) {
        int mod19 = year % 19;
        return mod19 == 0 || mod19 == 3 || mod19 == 6 || mod19 == 8 || mod19 == 11 || mod19 == 14 || mod19 == 17;
    }

    public int lengthOfMonth() {
        return lengthOfMonth(year, month);
    }

    /** ה-1 של אותו חודש באותה שנה. */
    @NonNull
    public HebrewDate firstOfMonth() {
        return new HebrewDate(year, month, 1);
    }

    @NonNull
    public static HebrewDate fromGregorian(@NonNull LocalDate gregorianDate) {
        HebrewCalendar cal = newCalendar();
        cal.set(Calendar.JULIAN_DAY, Math.toIntExact(gregorianDate.toEpochDay()) + JULIAN_DAY_OF_EPOCH);

        int year = cal.get(Calendar.YEAR);
        int icuMonth = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return new HebrewDate(year, fromIcuMonth(icuMonth, isLeapYear(year)), day);
    }

    @NonNull
    public LocalDate toGregorian() {
        HebrewCalendar cal = newCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, toIcuMonth(month));
        cal.set(Calendar.DAY_OF_MONTH, day);

        return LocalDate.ofEpochDay(cal.get(Calendar.JULIAN_DAY) - JULIAN_DAY_OF_EPOCH);
    }

    private static HebrewCalendar newCalendar() {
        HebrewCalendar cal = new HebrewCalendar(TimeZone.getTimeZone("GMT"));
        cal.clear();
        return cal;
    }

    /** אורך חודש (29 או 30). מניח שהשנה והחודש כבר נבדקו. */
    private static int lengthOfMonth(int year, int month) {
        HebrewCalendar cal = newCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, toIcuMonth(month));
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /** חודשי ICU: TISHRI=0 ... SHEVAT=4, ADAR_1=5, ADAR=6, NISAN=7 ... ELUL=12 */
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

    /** ההפך של fromIcuMonth: המספור שלנו (1-14) לחודש ב-ICU. */
    private static int toIcuMonth(int month) {
        switch (month) {
            case 13:
                return HebrewCalendar.ADAR_1;    // אדר א'
            case 12:                             // אדר (שנה פשוטה)
            case 14:                             // אדר ב' (שנה מעוברת)
                return HebrewCalendar.ADAR;
            default:
                return month >= 7
                        ? month - 7              // תשרי..שבט -> 0..4
                        : month + 6;             // ניסן..אלול -> 7..12
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HebrewDate that = (HebrewDate) o;
        return year == that.year && month == that.month && day == that.day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day);
    }

    @NonNull
    @Override
    public String toString() {
        return "HebrewDate{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }
}
