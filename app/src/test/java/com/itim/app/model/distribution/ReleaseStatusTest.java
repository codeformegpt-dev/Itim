package com.itim.app.model.distribution;

import static org.junit.Assert.*;

import org.junit.Test;

public class ReleaseStatusTest {

    /** The names are persisted/transmitted as strings, so renaming a constant is a breaking change. */
    @Test
    public void namesAreStable() {
        assertEquals(ReleaseStatus.BUILDING, ReleaseStatus.valueOf("BUILDING"));
        assertEquals(ReleaseStatus.PUBLISHED, ReleaseStatus.valueOf("PUBLISHED"));
        assertEquals(ReleaseStatus.SUPERSEDED, ReleaseStatus.valueOf("SUPERSEDED"));
        assertEquals(ReleaseStatus.FAILED, ReleaseStatus.valueOf("FAILED"));
        assertEquals(4, ReleaseStatus.values().length);
    }
}
