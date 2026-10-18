package com.itim.app.model.content;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommentaryTest {

    private static Commentary sample() {
        return new Commentary(1L, 10L, 20L, "פסוק א", 3, "טקסט הפירוש");
    }

    @Test
    public void getters() {
        Commentary c = sample();
        assertEquals(1L, c.getId());
        assertEquals(10L, c.getComponentId());
        assertEquals(20L, c.getCommentatorId());
        assertEquals("פסוק א", c.getTitle());
        assertEquals(3, c.getOrderIndex());
        assertEquals("טקסט הפירוש", c.getText());
    }

    @Test
    public void titleMayBeNull() {
        Commentary c = new Commentary(1L, 10L, 20L, null, 0, "טקסט");
        assertNull(c.getTitle());
    }

    @Test
    public void testEquals() {
        assertEquals(sample(), sample());
        assertNotEquals(sample(), new Commentary(2L, 10L, 20L, "פסוק א", 3, "טקסט הפירוש"));
        assertNotEquals(sample(), new Commentary(1L, 11L, 20L, "פסוק א", 3, "טקסט הפירוש"));
        assertNotEquals(sample(), new Commentary(1L, 10L, 21L, "פסוק א", 3, "טקסט הפירוש"));
        assertNotEquals(sample(), new Commentary(1L, 10L, 20L, null, 3, "טקסט הפירוש"));
        assertNotEquals(sample(), new Commentary(1L, 10L, 20L, "פסוק א", 4, "טקסט הפירוש"));
        assertNotEquals(sample(), new Commentary(1L, 10L, 20L, "פסוק א", 3, "טקסט אחר"));
        assertNotEquals(sample(), null);
        assertNotEquals(sample(), "not a commentary");
    }

    @Test
    public void testHashCode() {
        assertEquals(sample().hashCode(), sample().hashCode());
    }
}
