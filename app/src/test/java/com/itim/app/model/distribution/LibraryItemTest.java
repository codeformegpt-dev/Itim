package com.itim.app.model.distribution;

import static org.junit.Assert.*;

import com.itim.app.model.distribution.LibraryItem.LibraryState;

import org.junit.Test;

public class LibraryItemTest {

    private static SourceDownload download(Integer installed, Integer target, Integer total, Integer completed) {
        return new SourceDownload(1L, installed, target, total, completed, null);
    }

    @Test
    public void nullDownloadIsAvailable() {
        LibraryItem item = LibraryItem.of(1L, "תורה", null, false);
        assertEquals(1L, item.getSourceId());
        assertEquals("תורה", item.getLabel());
        assertEquals(LibraryState.AVAILABLE, item.getState());
        assertNull(item.getProgressPercent());
    }

    @Test
    public void downloadWithoutVersionsIsAvailable() {
        LibraryItem item = LibraryItem.of(1L, "תורה", download(null, null, null, null), true);
        assertEquals(LibraryState.AVAILABLE, item.getState());
        assertNull(item.getProgressPercent());
    }

    @Test
    public void installedVersionIsInstalled() {
        LibraryItem item = LibraryItem.of(1L, "תורה", download(3, null, null, null), false);
        assertEquals(LibraryState.INSTALLED, item.getState());
        assertNull(item.getProgressPercent());
    }

    @Test
    public void installedWinsOverPendingUpdate() {
        LibraryItem item = LibraryItem.of(1L, "תורה", download(3, 4, 10, 5), true);
        assertEquals(LibraryState.INSTALLED, item.getState());
        assertNull(item.getProgressPercent());
    }

    @Test
    public void runningDownloadIsDownloading() {
        LibraryItem item = LibraryItem.of(1L, "תורה", download(null, 1, 4, 3), true);
        assertEquals(LibraryState.DOWNLOADING, item.getState());
        assertEquals(Integer.valueOf(75), item.getProgressPercent());
    }

    @Test
    public void stoppedDownloadIsPaused() {
        LibraryItem item = LibraryItem.of(1L, "תורה", download(null, 1, 4, 1), false);
        assertEquals(LibraryState.PAUSED, item.getState());
        assertEquals(Integer.valueOf(25), item.getProgressPercent());
    }

    @Test
    public void progressRoundsDown() {
        assertEquals(Integer.valueOf(33), LibraryItem.of(1L, "x", download(null, 1, 3, 1), true).getProgressPercent());
        assertEquals(Integer.valueOf(0), LibraryItem.of(1L, "x", download(null, 1, 3, 0), true).getProgressPercent());
        assertEquals(Integer.valueOf(100), LibraryItem.of(1L, "x", download(null, 1, 3, 3), true).getProgressPercent());
    }

    @Test(expected = IllegalArgumentException.class)
    public void progressAbove100IsRejected() {
        LibraryItem.of(1L, "x", download(null, 1, 2, 3), true);
    }

    @Test
    public void testEquals() {
        LibraryItem a = LibraryItem.of(1L, "x", download(null, 1, 4, 2), true);
        assertEquals(a, LibraryItem.of(1L, "x", download(null, 1, 4, 2), true));
        assertNotEquals(a, LibraryItem.of(2L, "x", download(null, 1, 4, 2), true));
        assertNotEquals(a, LibraryItem.of(1L, "y", download(null, 1, 4, 2), true));
        assertNotEquals(a, LibraryItem.of(1L, "x", download(null, 1, 4, 2), false));
        assertNotEquals(a, LibraryItem.of(1L, "x", download(null, 1, 4, 3), true));
        assertNotEquals(a, null);
    }

    @Test
    public void testHashCode() {
        assertEquals(LibraryItem.of(1L, "x", download(null, 1, 4, 2), true).hashCode(),
                LibraryItem.of(1L, "x", download(null, 1, 4, 2), true).hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("LibraryItem{sourceId=1, label='x', state=DOWNLOADING, progressPercent=50}",
                LibraryItem.of(1L, "x", download(null, 1, 4, 2), true).toString());
    }
}
