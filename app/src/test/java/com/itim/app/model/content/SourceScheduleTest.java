package com.itim.app.model.content;

import static org.junit.Assert.*;

import org.junit.Test;

import java.time.LocalDate;

public class SourceScheduleTest {

    private static final LocalDate DATE = LocalDate.of(2026, 10, 19);

    private static SourceSchedule sample() {
        return new SourceSchedule(1L, 2L, DATE, 100L, 110L, "פרשת לך לך");
    }

    @Test
    public void getters() {
        SourceSchedule s = sample();
        assertEquals(1L, s.getId());
        assertEquals(2L, s.getSourceId());
        assertEquals(DATE, s.getDate());
        assertEquals(100L, s.getStartComponentId());
        assertEquals(110L, s.getEndComponentId());
        assertEquals("פרשת לך לך", s.getTitle());
    }

    @Test
    public void titleMayBeNull() {
        assertNull(new SourceSchedule(1L, 2L, DATE, 100L, 100L, null).getTitle());
    }

    @Test
    public void testEquals() {
        assertEquals(sample(), sample());
        assertNotEquals(sample(), new SourceSchedule(9L, 2L, DATE, 100L, 110L, "פרשת לך לך"));
        assertNotEquals(sample(), new SourceSchedule(1L, 9L, DATE, 100L, 110L, "פרשת לך לך"));
        assertNotEquals(sample(), new SourceSchedule(1L, 2L, DATE.plusDays(1), 100L, 110L, "פרשת לך לך"));
        assertNotEquals(sample(), new SourceSchedule(1L, 2L, DATE, 101L, 110L, "פרשת לך לך"));
        assertNotEquals(sample(), new SourceSchedule(1L, 2L, DATE, 100L, 111L, "פרשת לך לך"));
        assertNotEquals(sample(), new SourceSchedule(1L, 2L, DATE, 100L, 110L, null));
        assertNotEquals(sample(), null);
    }

    @Test
    public void testHashCode() {
        assertEquals(sample().hashCode(), sample().hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("SourceSchedule{id=1, sourceId=2, date=2026-10-19, startComponentId=100, endComponentId=110, title='פרשת לך לך'}",
                sample().toString());
    }
}
