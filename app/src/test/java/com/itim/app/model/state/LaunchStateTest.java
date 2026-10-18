package com.itim.app.model.state;

import static org.junit.Assert.*;

import org.junit.Test;

public class LaunchStateTest {

    @Test
    public void getters() {
        LaunchState s = new LaunchState(1L, Mode.DAILY, 5L);
        assertEquals(1L, s.getId());
        assertEquals(Mode.DAILY, s.getMode());
        assertEquals(Long.valueOf(5L), s.getSourceId());
    }

    @Test
    public void firstLaunchHasNoModeOrSource() {
        LaunchState s = new LaunchState(1L, null, null);
        assertNull(s.getMode());
        assertNull(s.getSourceId());
    }

    @Test
    public void testEquals() {
        LaunchState a = new LaunchState(1L, Mode.PERSONAL, 1000L);
        assertEquals(a, new LaunchState(1L, Mode.PERSONAL, 1000L));
        assertEquals(new LaunchState(1L, null, null), new LaunchState(1L, null, null));
        assertNotEquals(a, new LaunchState(2L, Mode.PERSONAL, 1000L));
        assertNotEquals(a, new LaunchState(1L, Mode.DAILY, 1000L));
        assertNotEquals(a, new LaunchState(1L, Mode.PERSONAL, 1001L));
        assertNotEquals(a, new LaunchState(1L, Mode.PERSONAL, null));
        assertNotEquals(a, null);
    }

    @Test
    public void testHashCode() {
        assertEquals(new LaunchState(1L, Mode.PERSONAL, 1000L).hashCode(),
                new LaunchState(1L, Mode.PERSONAL, 1000L).hashCode());
    }
}
