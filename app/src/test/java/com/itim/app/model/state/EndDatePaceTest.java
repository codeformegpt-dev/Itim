package com.itim.app.model.state;

import static org.junit.Assert.*;

import org.junit.Test;

import java.time.LocalDate;

public class EndDatePaceTest {

    private static final LocalDate START = LocalDate.of(2026, 10, 18);
    private static final LocalDate END = LocalDate.of(2027, 10, 18);

    private static EndDatePace sample() {
        return new EndDatePace(3L, START, END);
    }

    @Test
    public void getters() {
        EndDatePace p = sample();
        assertEquals(3L, p.getDailyScheduleId());
        assertEquals(START, p.getStartDate());
        assertEquals(END, p.getTargetEndDate());
    }

    @Test
    public void isStudyPace() {
        StudyPace pace = sample();
        assertEquals(3L, pace.getDailyScheduleId());
    }

    @Test
    public void testEquals() {
        assertEquals(sample(), sample());
        assertNotEquals(sample(), new EndDatePace(4L, START, END));
        assertNotEquals(sample(), new EndDatePace(3L, START.plusDays(1), END));
        assertNotEquals(sample(), new EndDatePace(3L, START, END.plusDays(1)));
        assertNotEquals(sample(), null);
    }

    @Test
    public void testHashCode() {
        assertEquals(sample().hashCode(), sample().hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("EndDatePace{dailyScheduleId=3, startDate=2026-10-18, targetEndDate=2027-10-18}", sample().toString());
    }
}
