package com.itim.app.model.settings;

import static org.junit.Assert.*;

import org.junit.Test;

public class TextMarksTest {

    /** TextMarks is persisted by name (DisplaySettings), so renaming a constant is a breaking change. */
    @Test
    public void namesAreStable() {
        assertEquals(TextMarks.ALL, TextMarks.valueOf("ALL"));
        assertEquals(TextMarks.NIKUD, TextMarks.valueOf("NIKUD"));
        assertEquals(TextMarks.NONE, TextMarks.valueOf("NONE"));
        assertEquals(3, TextMarks.values().length);
    }
}
