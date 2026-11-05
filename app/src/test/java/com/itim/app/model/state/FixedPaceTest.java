package com.itim.app.model.state;

import static org.junit.Assert.*;

import org.junit.Test;

public class FixedPaceTest {

    private static FixedPace sample() {
        return new FixedPace(7L, 3L);
    }

    @Test
    public void getters() {
        FixedPace p = sample();
        assertEquals(7L, p.getSourceScheduleId());
        assertEquals(3L, p.getDailyScheduleId());
    }

    @Test
    public void isStudyPace() {
        StudyPace pace = sample();
        assertEquals(3L, pace.getDailyScheduleId());
    }

    @Test
    public void testEquals() {
        assertEquals(sample(), sample());
        assertNotEquals(sample(), new FixedPace(8L, 3L));
        assertNotEquals(sample(), new FixedPace(7L, 4L));
        assertNotEquals(sample(), null);
    }

    @Test
    public void testHashCode() {
        assertEquals(sample().hashCode(), sample().hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("FixedPace{dailyScheduleId=3, sourceScheduleId=7}", sample().toString());
    }
}
