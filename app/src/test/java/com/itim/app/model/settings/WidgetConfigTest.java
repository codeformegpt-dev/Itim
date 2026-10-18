package com.itim.app.model.settings;

import static org.junit.Assert.*;

import org.junit.Test;

public class WidgetConfigTest {

    @Test
    public void getters() {
        WidgetConfig w = new WidgetConfig(7, 3L);
        assertEquals(7, w.getId());
        assertEquals(3L, w.getSourceId());
    }

    @Test
    public void testEquals() {
        assertEquals(new WidgetConfig(7, 3L), new WidgetConfig(7, 3L));
        assertNotEquals(new WidgetConfig(7, 3L), new WidgetConfig(8, 3L));
        assertNotEquals(new WidgetConfig(7, 3L), new WidgetConfig(7, 4L));
        assertNotEquals(new WidgetConfig(7, 3L), null);
    }

    /** sourceId is stored as a boxed Long; ids outside the Long cache (-128..127) must still be equal. */
    @Test
    public void testEqualsWithLargeSourceId() {
        assertEquals(new WidgetConfig(7, 1000L), new WidgetConfig(7, 1000L));
    }

    @Test
    public void testHashCode() {
        assertEquals(new WidgetConfig(7, 1000L).hashCode(), new WidgetConfig(7, 1000L).hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("WidgetConfig{id=7, sourceId=3}", new WidgetConfig(7, 3L).toString());
    }
}
