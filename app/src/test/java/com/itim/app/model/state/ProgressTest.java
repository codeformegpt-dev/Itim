package com.itim.app.model.state;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProgressTest {

    private static Progress sample() {
        return new Progress(1L, Mode.DAILY, 2L, 30L);
    }

    @Test
    public void getters() {
        Progress p = sample();
        assertEquals(1L, p.getId());
        assertEquals(Mode.DAILY, p.getMode());
        assertEquals(2L, p.getSourceId());
        assertEquals(30L, p.getComponentId());
    }

    @Test
    public void testEquals() {
        assertEquals(sample(), sample());
        assertNotEquals(sample(), new Progress(9L, Mode.DAILY, 2L, 30L));
        assertNotEquals(sample(), new Progress(1L, Mode.PERSONAL, 2L, 30L));
        assertNotEquals(sample(), new Progress(1L, Mode.DAILY, 9L, 30L));
        assertNotEquals(sample(), new Progress(1L, Mode.DAILY, 2L, 31L));
        assertNotEquals(sample(), null);
    }

    @Test
    public void testHashCode() {
        assertEquals(sample().hashCode(), sample().hashCode());
    }
}
