package com.itim.app.model.settings;

import static org.junit.Assert.*;

import org.junit.Test;

public class FontFamilyTest {

    /** FontFamily is persisted by name (DisplaySettings), so renaming a constant is a breaking change. */
    @Test
    public void namesAreStable() {
        assertEquals(FontFamily.TAAMEY_FRANK, FontFamily.valueOf("TAAMEY_FRANK"));
        assertEquals(FontFamily.EZRA, FontFamily.valueOf("EZRA"));
        assertEquals(FontFamily.NOTO_SANS, FontFamily.valueOf("NOTO_SANS"));
        assertEquals(3, FontFamily.values().length);
    }
}
