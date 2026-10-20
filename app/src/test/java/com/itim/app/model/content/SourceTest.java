package com.itim.app.model.content;

import static org.junit.Assert.*;

import org.junit.Test;

public class SourceTest {

    private static Source sample() {
        return new Source(3L, "תורה");
    }

    @Test
    public void getters() {
        Source s = sample();
        assertEquals(3L, s.getId());
        assertEquals("תורה", s.getLabel());
    }

    @Test
    public void testEquals() {
        assertEquals(sample(), sample());
        assertNotEquals(sample(), new Source(4L, "תורה"));
        assertNotEquals(sample(), new Source(3L, "נביאים"));
        assertNotEquals(sample(), null);
    }

    @Test
    public void testHashCode() {
        assertEquals(sample().hashCode(), sample().hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("Source{id=3, label='תורה'}", sample().toString());
    }
}
