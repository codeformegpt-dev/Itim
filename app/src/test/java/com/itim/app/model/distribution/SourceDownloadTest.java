package com.itim.app.model.distribution;

import static org.junit.Assert.*;

import org.junit.Test;

import java.time.Instant;

public class SourceDownloadTest {

    private static final Instant CHECKED = Instant.parse("2026-10-19T08:00:00Z");

    private static SourceDownload sample() {
        return new SourceDownload(1L, 2, 3, 10, 4, CHECKED);
    }

    @Test
    public void getters() {
        SourceDownload d = sample();
        assertEquals(1L, d.getSourceId());
        assertEquals(Integer.valueOf(2), d.getInstalledVersion());
        assertEquals(Integer.valueOf(3), d.getTargetVersion());
        assertEquals(Integer.valueOf(10), d.getTotalParts());
        assertEquals(Integer.valueOf(4), d.getCompletedParts());
        assertEquals(CHECKED, d.getLastCheckedAt());
    }

    @Test
    public void nullableFieldsMayBeNull() {
        SourceDownload d = new SourceDownload(1L, null, null, null, null, null);
        assertNull(d.getInstalledVersion());
        assertNull(d.getTargetVersion());
        assertNull(d.getTotalParts());
        assertNull(d.getCompletedParts());
        assertNull(d.getLastCheckedAt());
    }

    @Test
    public void testEquals() {
        assertEquals(sample(), sample());
        // boxed Integers outside the cache must still compare equal
        assertEquals(new SourceDownload(1L, 1000, 2000, 3000, 4000, null),
                new SourceDownload(1L, 1000, 2000, 3000, 4000, null));
        assertNotEquals(sample(), new SourceDownload(9L, 2, 3, 10, 4, CHECKED));
        assertNotEquals(sample(), new SourceDownload(1L, null, 3, 10, 4, CHECKED));
        assertNotEquals(sample(), new SourceDownload(1L, 2, 9, 10, 4, CHECKED));
        assertNotEquals(sample(), new SourceDownload(1L, 2, 3, 9, 4, CHECKED));
        assertNotEquals(sample(), new SourceDownload(1L, 2, 3, 10, 9, CHECKED));
        assertNotEquals(sample(), new SourceDownload(1L, 2, 3, 10, 4, CHECKED.plusSeconds(1)));
        assertNotEquals(sample(), null);
    }

    @Test
    public void testHashCode() {
        assertEquals(sample().hashCode(), sample().hashCode());
    }
}
