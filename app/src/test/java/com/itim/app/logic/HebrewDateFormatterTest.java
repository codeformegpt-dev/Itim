package com.itim.app.logic;

import static org.junit.Assert.*;

import com.itim.app.model.hebrew.HebrewDate;

import org.junit.Test;

public class HebrewDateFormatterTest {

    @Test
    public void format() {
        assertEquals("כ\"ז בתשרי", HebrewDateFormatter.format(new HebrewDate(5787, 7, 27)));
        assertEquals("ט\"ו בניסן", HebrewDateFormatter.format(new HebrewDate(5785, 1, 15)));
    }

    @Test
    public void fullFormat() {
        assertEquals("כ\"ז בתשרי תשפ\"ז", HebrewDateFormatter.fullFormat(new HebrewDate(5787, 7, 27)));
        assertEquals("י\"ד באדר ב' תשפ\"ד", HebrewDateFormatter.fullFormat(new HebrewDate(5784, 14, 14)));
    }

    @Test
    public void formatDay() {
        assertEquals("ג'", HebrewDateFormatter.formatDay(new HebrewDate(5787, 7, 3)));
        assertEquals("ט\"ו", HebrewDateFormatter.formatDay(new HebrewDate(5787, 7, 15)));
        assertEquals("ט\"ז", HebrewDateFormatter.formatDay(new HebrewDate(5787, 7, 16)));
        assertEquals("כ\"ז", HebrewDateFormatter.formatDay(new HebrewDate(5787, 7, 27)));
        assertEquals("ל'", HebrewDateFormatter.formatDay(new HebrewDate(5787, 7, 30)));
    }

    @Test
    public void formatMonth() {
        String[] expected = {"ניסן", "אייר", "סיון", "תמוז", "אב", "אלול", "תשרי",
                "חשון", "כסלו", "טבת", "שבט", "אדר", "אדר א'", "אדר ב'"};
        for (int month = 1; month <= 14; month++)
            assertEquals(expected[month - 1], HebrewDateFormatter.formatMonth(new HebrewDate(5787, month, 1)));
    }

    @Test
    public void formatYear() {
        assertEquals("תשפ\"ז", HebrewDateFormatter.formatYear(new HebrewDate(5787, 7, 1)));
        assertEquals("תשפ\"ה", HebrewDateFormatter.formatYear(new HebrewDate(5785, 7, 1)));
        assertEquals("תש\"ח", HebrewDateFormatter.formatYear(new HebrewDate(5708, 7, 1)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void formatYearRejectsMultipleOf1000() {
        HebrewDateFormatter.formatYear(new HebrewDate(6000, 7, 1));
    }

    @Test
    public void toGematria() {
        assertEquals("א'", HebrewDateFormatter.toGematria(1));
        assertEquals("י'", HebrewDateFormatter.toGematria(10));
        assertEquals("י\"א", HebrewDateFormatter.toGematria(11));
        assertEquals("ט\"ו", HebrewDateFormatter.toGematria(15));
        assertEquals("ט\"ז", HebrewDateFormatter.toGematria(16));
        assertEquals("י\"ז", HebrewDateFormatter.toGematria(17));
        assertEquals("ק'", HebrewDateFormatter.toGematria(100));
        assertEquals("קט\"ו", HebrewDateFormatter.toGematria(115));
        assertEquals("ת'", HebrewDateFormatter.toGematria(400));
        assertEquals("תשפ\"ו", HebrewDateFormatter.toGematria(786));
        assertEquals("תתקצ\"ט", HebrewDateFormatter.toGematria(999));
    }

    @Test(expected = IllegalArgumentException.class)
    public void toGematriaRejectsZero() {
        HebrewDateFormatter.toGematria(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void toGematriaRejects1000() {
        HebrewDateFormatter.toGematria(1000);
    }
}
