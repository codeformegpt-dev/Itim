package com.itim.app.data.remote;

import static org.junit.Assert.*;

import org.junit.Test;

public class SourceReleaseTest {

    @Test
    public void getters() {
        SourceRelease r = new SourceRelease(1L, 3, 12);
        assertEquals(1L, r.getSourceId());
        assertEquals(3, r.getVersion());
        assertEquals(12, r.getTotalParts());
    }

    @Test
    public void testEquals() {
        SourceRelease a = new SourceRelease(1L, 3, 12);
        assertEquals(a, new SourceRelease(1L, 3, 12));
        assertNotEquals(a, new SourceRelease(2L, 3, 12));
        assertNotEquals(a, new SourceRelease(1L, 4, 12));
        assertNotEquals(a, new SourceRelease(1L, 3, 13));
        assertNotEquals(a, null);
    }

    @Test
    public void testHashCode() {
        assertEquals(new SourceRelease(1L, 3, 12).hashCode(), new SourceRelease(1L, 3, 12).hashCode());
    }
}
