package com.itim.app.model.hebrew;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchResultTest {

    private static final List<String> PATH = Arrays.asList("תורה", "בראשית", "פרק א");

    private static SearchResult sample() {
        return new SearchResult(10L, 1L, true, PATH, "בראשית ברא");
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
        assertFalse(new SearchResult(10L, 1L, false, PATH, "x").isInstalled());
    }

    @Test
    public void getPath() {
        assertEquals(PATH, sample().getPath());
    }

    @Test
    public void pathIsDefensivelyCopied() {
        List<String> path = new ArrayList<>(PATH);
        SearchResult r = new SearchResult(10L, 1L, true, path, "x");
        path.add("פסוק א");
        assertEquals(PATH, r.getPath());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void pathIsUnmodifiable() {
        sample().getPath().add("x");
    }

    @Test
    public void getSnippet() {
        assertEquals("בראשית ברא", sample().getSnippet());
    }

    @Test(expected = NullPointerException.class)
    public void snippetMustNotBeNull() {
        new SearchResult(10L, 1L, true, PATH, null);
    }

    @Test
    public void testEquals() {
        // זהות לפי componentId בלבד
        assertEquals(sample(), new SearchResult(10L, 2L, false, List.of("אחר"), "אחר"));
        assertNotEquals(sample(), new SearchResult(11L, 1L, true, PATH, "בראשית ברא"));
        assertNotEquals(sample(), null);
    }

    @Test
    public void testHashCode() {
        assertEquals(sample().hashCode(), new SearchResult(10L, 2L, false, List.of(), "אחר").hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("SearchResult{componentId=10, sourceId=1, installed=true, path=[תורה, בראשית, פרק א], snippet='בראשית ברא'}",
                sample().toString());
    }
}
