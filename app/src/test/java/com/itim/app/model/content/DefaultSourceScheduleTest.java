package com.itim.app.model.content;

import static org.junit.Assert.*;

import org.junit.Test;

public class DefaultSourceScheduleTest {

    private static DefaultSourceSchedule sample() {
        return new DefaultSourceSchedule(1L, 5L);
    }

    @Test
    public void getters() {
        DefaultSourceSchedule d = sample();
        assertEquals(1L, d.getSourceId());
        assertEquals(5L, d.getSourceScheduleId());
    }

    @Test
    public void testEquals() {
        assertEquals(sample(), sample());
        assertNotEquals(sample(), new DefaultSourceSchedule(2L, 5L));
        assertNotEquals(sample(), new DefaultSourceSchedule(1L, 6L));
        assertNotEquals(sample(), null);
    }

    @Test
    public void testHashCode() {
        assertEquals(sample().hashCode(), sample().hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("DefaultSourceSchedule{sourceId=1, sourceScheduleId=5}", sample().toString());
    }
}
