package com.itim.app.model.state;

import static org.junit.Assert.*;

import com.itim.app.model.state.DailyStatus.DailyState;

import org.junit.Test;

public class DailyStatusTest {

    private static DailyStatus sample() {
        return new DailyStatus(1L, "תורה", "פרשת נח", DailyState.IN_PROGRESS);
    }

    @Test
    public void getters() {
        DailyStatus s = sample();
        assertEquals(1L, s.getSourceId());
        assertEquals("תורה", s.getLabel());
        assertEquals("פרשת נח", s.getPortionTitle());
        assertEquals(DailyState.IN_PROGRESS, s.getState());
    }

    @Test
    public void portionTitleMayBeNull() {
        assertNull(new DailyStatus(1L, "תורה", null, DailyState.NO_PORTION).getPortionTitle());
    }

    @Test
    public void testEquals() {
        assertEquals(sample(), sample());
        assertNotEquals(sample(), new DailyStatus(2L, "תורה", "פרשת נח", DailyState.IN_PROGRESS));
        assertNotEquals(sample(), new DailyStatus(1L, "נביאים", "פרשת נח", DailyState.IN_PROGRESS));
        assertNotEquals(sample(), new DailyStatus(1L, "תורה", null, DailyState.IN_PROGRESS));
        assertNotEquals(sample(), new DailyStatus(1L, "תורה", "פרשת נח", DailyState.COMPLETED));
        assertNotEquals(sample(), null);
    }

    @Test
    public void testHashCode() {
        assertEquals(sample().hashCode(), sample().hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("DailyStatus{sourceId=1, label='תורה', portionTitle='פרשת נח', state=IN_PROGRESS}",
                sample().toString());
    }
}
