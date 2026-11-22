package com.itim.app.model.state;

import static org.junit.Assert.*;

import org.junit.Test;

import java.time.LocalDate;

public class DailyAmountPaceTest {

    private static final LocalDate START = LocalDate.of(2026, 10, 18);

    private static DailyAmountPace sample() {
        return new DailyAmountPace(3L, START, 2, 5);
    }

    @Test
    public void getters() {
        DailyAmountPace p = sample();
        assertEquals(3L, p.getDailyScheduleId());
        assertEquals(START, p.getStartDate());
        assertEquals(2, p.getUnitDepth());
        assertEquals(5, p.getUnitsPerDay());
    }

    @Test
    public void isStudyPace() {
        StudyPace pace = sample();
        assertEquals(3L, pace.getDailyScheduleId());
    }

    @Test
    public void constructorAcceptsMinimumValues() {
        DailyAmountPace p = new DailyAmountPace(1L, START, 1, 1);
        assertEquals(1, p.getUnitDepth());
        assertEquals(1, p.getUnitsPerDay());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorRejectsZeroUnitDepth() {
        new DailyAmountPace(3L, START, 0, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorRejectsNegativeUnitDepth() {
        new DailyAmountPace(3L, START, -1, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorRejectsZeroUnitsPerDay() {
        new DailyAmountPace(3L, START, 2, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorRejectsNegativeUnitsPerDay() {
        new DailyAmountPace(3L, START, 2, -3);
    }

    @Test
    public void testEquals() {
        assertEquals(sample(), sample());
        assertNotEquals(sample(), new DailyAmountPace(4L, START, 2, 5));
        assertNotEquals(sample(), new DailyAmountPace(3L, START.plusDays(1), 2, 5));
        assertNotEquals(sample(), new DailyAmountPace(3L, START, 3, 5));
        assertNotEquals(sample(), new DailyAmountPace(3L, START, 2, 6));
        assertNotEquals(sample(), null);
    }

    @Test
    public void testHashCode() {
        assertEquals(sample().hashCode(), sample().hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("DailyAmountPace{dailyScheduleId=3, startDate=2026-10-18, unitDepth=2, unitsPerDay=5}", sample().toString());
    }
}
