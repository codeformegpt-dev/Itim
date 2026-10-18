package com.itim.app.model.hebrew;

import static org.junit.Assert.*;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;

/** החלקים של HebrewDate שתלויים ב-android.icu, ולכן רצים על מכשיר/אמולטור. */
@RunWith(AndroidJUnit4.class)
public class HebrewDateInstrumentedTest {

    @Test
    public void fromGregorianRoshHashana() {
        assertEquals(new HebrewDate(5785, 7, 1), HebrewDate.fromGregorian(LocalDate.of(2024, 10, 3)));
        assertEquals(new HebrewDate(5786, 7, 1), HebrewDate.fromGregorian(LocalDate.of(2025, 9, 23)));
    }

    @Test
    public void fromGregorianNisan() {
        assertEquals(new HebrewDate(5785, 1, 15), HebrewDate.fromGregorian(LocalDate.of(2025, 4, 13)));
    }

    @Test
    public void fromGregorianAdarInRegularYear() {
        assertEquals(new HebrewDate(5785, 12, 14), HebrewDate.fromGregorian(LocalDate.of(2025, 3, 14)));
    }

    @Test
    public void fromGregorianAdarInLeapYear() {
        assertEquals(new HebrewDate(5784, 13, 14), HebrewDate.fromGregorian(LocalDate.of(2024, 2, 23)));
        assertEquals(new HebrewDate(5784, 14, 14), HebrewDate.fromGregorian(LocalDate.of(2024, 3, 24)));
    }

    @Test
    public void toGregorian() {
        assertEquals(LocalDate.of(2024, 10, 3), new HebrewDate(5785, 7, 1).toGregorian());
        assertEquals(LocalDate.of(2025, 4, 13), new HebrewDate(5785, 1, 15).toGregorian());
        assertEquals(LocalDate.of(2025, 3, 14), new HebrewDate(5785, 12, 14).toGregorian());
        assertEquals(LocalDate.of(2024, 2, 23), new HebrewDate(5784, 13, 14).toGregorian());
        assertEquals(LocalDate.of(2024, 3, 24), new HebrewDate(5784, 14, 14).toGregorian());
    }

    @Test
    public void roundTripOverSeveralYears() {
        LocalDate start = LocalDate.of(2023, 1, 1);
        LocalDate end = LocalDate.of(2028, 1, 1);
        for (LocalDate d = start; d.isBefore(end); d = d.plusDays(1))
            assertEquals(d.toString(), d, HebrewDate.fromGregorian(d).toGregorian());
    }

    @Test
    public void lengthOfMonth() {
        assertEquals(30, new HebrewDate(5785, 7, 1).lengthOfMonth());   // תשרי
        assertEquals(30, new HebrewDate(5785, 8, 1).lengthOfMonth());   // חשון בשנה שלמה
        assertEquals(29, new HebrewDate(5785, 12, 1).lengthOfMonth());  // אדר בשנה פשוטה
        assertEquals(30, new HebrewDate(5784, 13, 1).lengthOfMonth());  // אדר א'
        assertEquals(29, new HebrewDate(5784, 14, 1).lengthOfMonth());  // אדר ב'
        assertEquals(30, new HebrewDate(5785, 1, 1).lengthOfMonth());   // ניסן
        assertEquals(29, new HebrewDate(5785, 2, 1).lengthOfMonth());   // אייר
        assertEquals(29, new HebrewDate(5785, 6, 1).lengthOfMonth());   // אלול
    }
}
