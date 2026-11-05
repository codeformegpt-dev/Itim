package com.itim.app.model.content;

import static org.junit.Assert.*;

import org.junit.Test;

public class SourceLevelTest {

    private static SourceLevel sample() {
        return new SourceLevel(1L, 2, "פרק");
    }

    @Test
    public void getters() {
        SourceLevel l = sample();
        assertEquals(1L, l.getSourceId());
        assertEquals(2, l.getDepth());
        assertEquals("פרק", l.getName());
    }

    @Test
    public void depthOneIsAllowed() {
        assertEquals(1, new SourceLevel(1L, 1, "ספר").getDepth());
    }

    @Test(expected = IllegalArgumentException.class)
    public void depthZeroIsRejected() {
        new SourceLevel(1L, 0, "ספר");
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeDepthIsRejected() {
        new SourceLevel(1L, -1, "ספר");
    }

    @Test
    public void testEquals() {
        assertEquals(sample(), sample());
        assertNotEquals(sample(), new SourceLevel(9L, 2, "פרק"));
        assertNotEquals(sample(), new SourceLevel(1L, 3, "פרק"));
        assertNotEquals(sample(), new SourceLevel(1L, 2, "פסוק"));
        assertNotEquals(sample(), null);
    }

    @Test
    public void testHashCode() {
        assertEquals(sample().hashCode(), sample().hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("SourceLevel{sourceId=1, depth=2, name='פרק'}", sample().toString());
    }
}
