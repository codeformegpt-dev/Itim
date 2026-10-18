package com.itim.app.model.state;

import static org.junit.Assert.*;

import org.junit.Test;

import java.time.LocalDate;

public class DailyScheduleTest {

    private static final LocalDate DATE = LocalDate.of(2026, 10, 18);

    private static DailySchedule sample() {
        return new DailySchedule(1L, 2L, -3, DATE);
    }

    @Test
    public void getters() {
        DailySchedule s = sample();
        assertEquals(1L, s.getId());
        assertEquals(2L, s.getSourceId());
        assertEquals(-3, s.getOffsetDays());
        assertEquals(DATE, s.getLastCompletedDate());
    }

    @Test
    public void lastCompletedDateMayBeNull() {
        assertNull(new DailySchedule(1L, 2L, 0, null).getLastCompletedDate());
    }

    @Test
    public void testEquals() {
        assertEquals(sample(), sample());
        assertNotEquals(sample(), new DailySchedule(9L, 2L, -3, DATE));
        assertNotEquals(sample(), new DailySchedule(1L, 9L, -3, DATE));
        assertNotEquals(sample(), new DailySchedule(1L, 2L, 0, DATE));
        assertNotEquals(sample(), new DailySchedule(1L, 2L, -3, DATE.plusDays(1)));
        assertNotEquals(sample(), new DailySchedule(1L, 2L, -3, null));
        assertNotEquals(sample(), null);
    }

    @Test
    public void testHashCode() {
        assertEquals(sample().hashCode(), sample().hashCode());
    }
}
