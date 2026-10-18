package com.itim.app.model.content;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommentatorTest {

    private static Commentator sample() {
        return new Commentator(1L, 10L, "רש\"י", true);
    }

    @Test
    public void getters() {
        Commentator c = sample();
        assertEquals(1L, c.getId());
        assertEquals(10L, c.getSourceId());
        assertEquals("רש\"י", c.getName());
        assertTrue(c.isBasic());
        assertFalse(new Commentator(2L, 10L, "אבן עזרא", false).isBasic());
    }

    @Test
    public void testEquals() {
        assertEquals(sample(), sample());
        assertNotEquals(sample(), new Commentator(2L, 10L, "רש\"י", true));
        assertNotEquals(sample(), new Commentator(1L, 11L, "רש\"י", true));
        assertNotEquals(sample(), new Commentator(1L, 10L, "רמב\"ן", true));
        assertNotEquals(sample(), new Commentator(1L, 10L, "רש\"י", false));
        assertNotEquals(sample(), null);
    }

    @Test
    public void testHashCode() {
        assertEquals(sample().hashCode(), sample().hashCode());
    }
}
