package com.itim.app.model.hebrew;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * בדיקות שלא נוגעות ב-android.icu. ההמרות לגרגוריאני ואורך החודש נבדקים ב-HebrewDateInstrumentedTest
 * (androidTest), כי ב-unit test רגיל ה-android.jar הוא stub שזורק "not mocked".
 */
public class HebrewDateTest {

    @Test
    public void getYear() {
        assertEquals(5787, new HebrewDate(5787, 7, 1).getYear());
    }

    @Test
    public void getMonth() {
        assertEquals(7, new HebrewDate(5787, 7, 1).getMonth());
    }

    @Test
    public void getDay() {
        assertEquals(27, new HebrewDate(5787, 7, 27).getDay());
    }

    @Test
    public void constructorAcceptsBounds() {
        new HebrewDate(1, 1, 1);
        new HebrewDate(5787, 14, 30);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorRejectsYearZero() {
        new HebrewDate(0, 1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorRejectsMonthZero() {
        new HebrewDate(5787, 0, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorRejectsMonth15() {
        new HebrewDate(5787, 15, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorRejectsDayZero() {
        new HebrewDate(5787, 1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorRejectsDay31() {
        new HebrewDate(5787, 1, 31);
    }

    @Test
    public void isLeapYear() {
        assertTrue(new HebrewDate(5784, 1, 1).isLeapYear());
        assertFalse(new HebrewDate(5785, 1, 1).isLeapYear());
    }

    @Test
    public void testIsLeapYear() {
        // שנים 3, 6, 8, 11, 14, 17, 19 במחזור של 19 שנה
        int[] leapInCycle = {3, 6, 8, 11, 14, 17, 19};
        for (int i = 1; i <= 19; i++) {
            boolean expected = false;
            for (int l : leapInCycle)
                if (l == i) expected = true;
            assertEquals("year " + (5776 + i), expected, HebrewDate.isLeapYear(5776 + i));
        }
        assertTrue(HebrewDate.isLeapYear(5784));
        assertFalse(HebrewDate.isLeapYear(5785));
        assertFalse(HebrewDate.isLeapYear(5786));
        assertTrue(HebrewDate.isLeapYear(5787));
    }

    @Test
    public void firstOfMonth() {
        assertEquals(new HebrewDate(5787, 13, 1), new HebrewDate(5787, 13, 22).firstOfMonth());
    }

    @Test
    public void testEquals() {
        HebrewDate d = new HebrewDate(5787, 7, 27);
        assertEquals(d, new HebrewDate(5787, 7, 27));
        assertNotEquals(d, new HebrewDate(5786, 7, 27));
        assertNotEquals(d, new HebrewDate(5787, 8, 27));
        assertNotEquals(d, new HebrewDate(5787, 7, 28));
        assertNotEquals(d, null);
    }

    @Test
    public void testHashCode() {
        assertEquals(new HebrewDate(5787, 7, 27).hashCode(), new HebrewDate(5787, 7, 27).hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("HebrewDate{year=5787, month=7, day=27}", new HebrewDate(5787, 7, 27).toString());
    }
}
