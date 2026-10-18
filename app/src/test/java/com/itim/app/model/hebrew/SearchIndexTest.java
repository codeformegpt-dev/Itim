package com.itim.app.model.hebrew;

import static org.junit.Assert.*;

import org.junit.Test;

public class SearchIndexTest {

    @Test
    public void getRowid() {
        assertEquals(42L, new SearchIndex(42L, "בראשית").getRowid());
    }

    @Test
    public void getNormalizedText() {
        assertEquals("בראשית ברא", new SearchIndex(42L, "בראשית ברא").getNormalizedText());
    }

    @Test
    public void testEquals() {
        SearchIndex a = new SearchIndex(1L, "בראשית");
        assertEquals(a, new SearchIndex(1L, "בראשית"));
        assertNotEquals(a, new SearchIndex(2L, "בראשית"));
        assertNotEquals(a, new SearchIndex(1L, "שמות"));
        assertNotEquals(a, null);
    }

    @Test
    public void testHashCode() {
        assertEquals(new SearchIndex(1L, "בראשית").hashCode(), new SearchIndex(1L, "בראשית").hashCode());
    }

    @Test
    public void testToStringShortText() {
        String s = new SearchIndex(1L, "בראשית").toString();
        assertTrue(s, s.contains("rowid=1"));
        assertTrue(s, s.contains("בראשית"));
    }

    @Test
    public void testToStringTruncatesLongText() {
        String head = "א".repeat(49);
        String s = new SearchIndex(1L, head + "ב".repeat(100)).toString();
        assertTrue(s, s.contains(head));
        assertFalse(s, s.contains("ב"));
    }
}
