package com.itim.app.data.personal;

import static org.junit.Assert.*;

import com.itim.app.model.state.DailyAmountPace;
import com.itim.app.model.state.DailySchedule;
import com.itim.app.model.state.EndDatePace;
import com.itim.app.model.state.FixedPace;

import org.junit.Test;

import java.time.LocalDate;

public class DailyScheduleWithPaceTest {

    private static final LocalDate START = LocalDate.of(2026, 10, 18);
    private static final DailySchedule SCHEDULE = new DailySchedule(3L, 1L, 0, null);
    private static final FixedPace FIXED = new FixedPace(3L, 7L);
    private static final EndDatePace END_DATE = new EndDatePace(3L, START, START.plusYears(1));
    private static final DailyAmountPace AMOUNT = new DailyAmountPace(3L, START, 2, 5);

    @Test
    public void getters() {
        DailyScheduleWithPace w = new DailyScheduleWithPace(SCHEDULE, FIXED, null, null);
        assertEquals(SCHEDULE, w.getDailySchedule());
        assertEquals(FIXED, w.getFixedPace());
        assertNull(w.getEndDatePace());
        assertNull(w.getDailyAmountPace());
    }

    @Test
    public void getPaceReturnsFixedPace() {
        assertSame(FIXED, new DailyScheduleWithPace(SCHEDULE, FIXED, null, null).getPace());
    }

    @Test
    public void getPaceReturnsEndDatePace() {
        assertSame(END_DATE, new DailyScheduleWithPace(SCHEDULE, null, END_DATE, null).getPace());
    }

    @Test
    public void getPaceReturnsDailyAmountPace() {
        assertSame(AMOUNT, new DailyScheduleWithPace(SCHEDULE, null, null, AMOUNT).getPace());
    }

    @Test
    public void getPaceIsNullWhenNoPace() {
        assertNull(new DailyScheduleWithPace(SCHEDULE, null, null, null).getPace());
    }

    @Test
    public void getPaceIsNullWhenMoreThanOnePace() {
        assertNull(new DailyScheduleWithPace(SCHEDULE, FIXED, END_DATE, null).getPace());
        assertNull(new DailyScheduleWithPace(SCHEDULE, FIXED, null, AMOUNT).getPace());
        assertNull(new DailyScheduleWithPace(SCHEDULE, null, END_DATE, AMOUNT).getPace());
        assertNull(new DailyScheduleWithPace(SCHEDULE, FIXED, END_DATE, AMOUNT).getPace());
    }

    @Test
    public void testEquals() {
        DailyScheduleWithPace a = new DailyScheduleWithPace(SCHEDULE, FIXED, null, null);
        assertEquals(a, new DailyScheduleWithPace(SCHEDULE, FIXED, null, null));
        assertNotEquals(a, new DailyScheduleWithPace(new DailySchedule(4L, 1L, 0, null), FIXED, null, null));
        assertNotEquals(a, new DailyScheduleWithPace(SCHEDULE, null, END_DATE, null));
        assertNotEquals(a, new DailyScheduleWithPace(SCHEDULE, FIXED, null, AMOUNT));
        assertNotEquals(a, null);
    }

    @Test
    public void testHashCode() {
        assertEquals(new DailyScheduleWithPace(SCHEDULE, FIXED, null, null).hashCode(),
                new DailyScheduleWithPace(SCHEDULE, FIXED, null, null).hashCode());
    }
}
