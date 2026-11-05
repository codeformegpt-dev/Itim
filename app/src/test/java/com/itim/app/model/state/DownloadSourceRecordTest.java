package com.itim.app.model.state;

import static org.junit.Assert.*;

import org.junit.Test;

public class DownloadSourceRecordTest {

    @Test
    public void getters() {
        assertEquals(4L, new DownloadSourceRecord(4L).getSourceId());
    }

    @Test
    public void testEquals() {
        assertEquals(new DownloadSourceRecord(4L), new DownloadSourceRecord(4L));
        assertNotEquals(new DownloadSourceRecord(4L), new DownloadSourceRecord(5L));
        assertNotEquals(new DownloadSourceRecord(4L), null);
    }

    @Test
    public void testHashCode() {
        assertEquals(new DownloadSourceRecord(4L).hashCode(), new DownloadSourceRecord(4L).hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("DownloadSourceRecord{SourceId=4}", new DownloadSourceRecord(4L).toString());
    }
}
