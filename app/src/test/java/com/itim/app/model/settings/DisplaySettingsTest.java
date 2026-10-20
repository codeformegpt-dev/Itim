package com.itim.app.model.settings;

import static org.junit.Assert.*;


import org.junit.Test;

public class DisplaySettingsTest {

    private static final int ID = DisplaySettings.SINGLETON_ID;

    @Test
    public void defaults() {
        DisplaySettings d = DisplaySettings.defaults;
        assertEquals(DisplaySettings.SINGLETON_ID, d.getId());
        assertEquals(DisplaySettings.DEFAULT_FONT_SIZE, d.getFontSize());
        assertEquals(DisplaySettings.DEFAULT_FONT_FAMILY, d.getFontFamily());
        assertEquals(DisplaySettings.DEFAULT_TEXT_MARKS, d.getTextMarks());
    }

    @Test
    public void getters() {
        DisplaySettings d = new DisplaySettings(ID, 30, FontFamily.EZRA, TextMarks.NIKUD);
        assertEquals(ID, d.getId());
        assertEquals(30, d.getFontSize());
        assertEquals(FontFamily.EZRA, d.getFontFamily());
        assertEquals(TextMarks.NIKUD, d.getTextMarks());
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsNonSingletonId() {
        new DisplaySettings(ID + 1, DisplaySettings.DEFAULT_FONT_SIZE, FontFamily.EZRA, TextMarks.ALL);
    }

    @Test
    public void isValidFontSizeAcceptsWholeRangeInSteps() {
        for (int size = DisplaySettings.MIN_FONT_SIZE; size <= DisplaySettings.MAX_FONT_SIZE; size += DisplaySettings.FONT_SIZE_STEP)
            assertTrue("expected " + size + " to be valid", DisplaySettings.isValidFontSize(size));
    }

    @Test
    public void isValidFontSizeRejectsOutOfRange() {
        assertFalse(DisplaySettings.isValidFontSize(DisplaySettings.MIN_FONT_SIZE - DisplaySettings.FONT_SIZE_STEP));
        assertFalse(DisplaySettings.isValidFontSize(DisplaySettings.MAX_FONT_SIZE + DisplaySettings.FONT_SIZE_STEP));
        assertFalse(DisplaySettings.isValidFontSize(0));
        assertFalse(DisplaySettings.isValidFontSize(-20));
    }

    @Test
    public void isValidFontSizeRejectsOffStep() {
        assertFalse(DisplaySettings.isValidFontSize(DisplaySettings.DEFAULT_FONT_SIZE + 1));
        assertFalse(DisplaySettings.isValidFontSize(DisplaySettings.MAX_FONT_SIZE - 1));
    }

    @Test
    public void constructorAcceptsMinAndMaxFontSize() {
        assertEquals(DisplaySettings.MIN_FONT_SIZE,
                new DisplaySettings(ID, DisplaySettings.MIN_FONT_SIZE, FontFamily.EZRA, TextMarks.ALL).getFontSize());
        assertEquals(DisplaySettings.MAX_FONT_SIZE,
                new DisplaySettings(ID, DisplaySettings.MAX_FONT_SIZE, FontFamily.EZRA, TextMarks.ALL).getFontSize());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorRejectsInvalidFontSize() {
        new DisplaySettings(ID, 21, FontFamily.EZRA, TextMarks.ALL);
    }

    @Test
    public void testEquals() {
        DisplaySettings a = new DisplaySettings(ID, 24, FontFamily.NOTO_SANS, TextMarks.NONE);
        assertEquals(a, new DisplaySettings(ID, 24, FontFamily.NOTO_SANS, TextMarks.NONE));
        assertNotEquals(a, new DisplaySettings(ID, 26, FontFamily.NOTO_SANS, TextMarks.NONE));
        assertNotEquals(a, new DisplaySettings(ID, 24, FontFamily.EZRA, TextMarks.NONE));
        assertNotEquals(a, new DisplaySettings(ID, 24, FontFamily.NOTO_SANS, TextMarks.ALL));
        assertNotEquals(a, null);
    }

    @Test
    public void testHashCode() {
        assertEquals(new DisplaySettings(ID, 24, FontFamily.NOTO_SANS, TextMarks.NONE).hashCode(),
                new DisplaySettings(ID, 24, FontFamily.NOTO_SANS, TextMarks.NONE).hashCode());
    }

    @Test
    public void withFontSizeChangesOnlyFontSize() {
        DisplaySettings d = DisplaySettings.defaults.withFontSize(30);
        assertEquals(new DisplaySettings(ID, 30, DisplaySettings.DEFAULT_FONT_FAMILY, DisplaySettings.DEFAULT_TEXT_MARKS), d);
        assertEquals(DisplaySettings.DEFAULT_FONT_SIZE, DisplaySettings.defaults.getFontSize());
    }

    @Test(expected = IllegalArgumentException.class)
    public void withFontSizeRejectsInvalidFontSize() {
        DisplaySettings.defaults.withFontSize(21);
    }

    @Test
    public void withFontFamilyChangesOnlyFontFamily() {
        DisplaySettings d = DisplaySettings.defaults.withFontFamily(FontFamily.EZRA);
        assertEquals(new DisplaySettings(ID, DisplaySettings.DEFAULT_FONT_SIZE, FontFamily.EZRA, DisplaySettings.DEFAULT_TEXT_MARKS), d);
        assertEquals(DisplaySettings.DEFAULT_FONT_FAMILY, DisplaySettings.defaults.getFontFamily());
    }

    @Test
    public void withTextMarksChangesOnlyTextMarks() {
        DisplaySettings d = DisplaySettings.defaults.withTextMarks(TextMarks.NONE);
        assertEquals(new DisplaySettings(ID, DisplaySettings.DEFAULT_FONT_SIZE, DisplaySettings.DEFAULT_FONT_FAMILY, TextMarks.NONE), d);
        assertEquals(DisplaySettings.DEFAULT_TEXT_MARKS, DisplaySettings.defaults.getTextMarks());
    }

    @Test
    public void testToString() {
        assertEquals("DisplaySettings{id=1, fontSize=20, fontFamily=TAAMEY_FRANK, textMarks=ALL}",
                DisplaySettings.defaults.toString());
    }
}
