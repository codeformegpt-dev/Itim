package com.itim.app.model.hebrew;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchSuggestionTest {

    private static final List<String> PATH = Arrays.asList("תורה", "בראשית");

    private static SearchSuggestion sample() {
        return new SearchSuggestion(10L, 1L, true, PATH);
    }

    @Test
    public void getComponentId() {
        assertEquals(10L, sample().getComponentId());
    }

    @Test
    public void getSourceId() {
        assertEquals(1L, sample().getSourceId());
    }

    @Test
    public void isInstalled() {
        assertTrue(sample().isInstalled());
        assertFalse(new SearchSuggestion(10L, 1L, false, PATH).isInstalled());
    }

    @Test
    public void getPath() {
        assertEquals(PATH, sample().getPath());
    }

    @Test
    public void pathIsDefensivelyCopied() {
        List<String> path = new ArrayList<>(PATH);
        SearchSuggestion s = new SearchSuggestion(10L, 1L, true, path);
        path.add("פרק א");
        assertEquals(PATH, s.getPath());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void pathIsUnmodifiable() {
        sample().getPath().add("x");
    }

    @Test
    public void testEquals() {
        // זהות לפי componentId בלבד
        assertEquals(sample(), new SearchSuggestion(10L, 2L, false, List.of()));
        assertNotEquals(sample(), new SearchSuggestion(11L, 1L, true, PATH));
        assertNotEquals(sample(), null);
    }

    @Test
    public void testHashCode() {
        assertEquals(sample().hashCode(), new SearchSuggestion(10L, 2L, false, List.of()).hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("SearchSuggestion{componentId=10, sourceId=1, installed=true, path=[תורה, בראשית]}",
                sample().toString());
    }
}
