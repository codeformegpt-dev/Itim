package com.itim.app.model.content;

import static org.junit.Assert.*;

import org.junit.Test;

public class SourceScheduleTest {

    private static SourceSchedule sample() {
        return new SourceSchedule(1L, 2L, "פרשת השבוע");
    }

    @Test
    public void getters() {
        SourceSchedule s = sample();
        assertEquals(1L, s.getId());
        assertEquals(2L, s.getSourceId());
        assertEquals("פרשת השבוע", s.getTitle());
    }

    @Test
    public void testEquals() {
        assertEquals(sample(), sample());
        assertNotEquals(sample(), new SourceSchedule(9L, 2L, "פרשת השבוע"));
        assertNotEquals(sample(), new SourceSchedule(1L, 9L, "פרשת השבוע"));
        assertNotEquals(sample(), new SourceSchedule(1L, 2L, "פרק ליום"));
        assertNotEquals(sample(), null);
    }

    @Test
    public void testHashCode() {
        assertEquals(sample().hashCode(), sample().hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("SourceSchedule{id=1, sourceId=2, title='פרשת השבוע'}", sample().toString());
    }
}
