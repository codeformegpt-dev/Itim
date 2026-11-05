package com.itim.app.model.state;

import static org.junit.Assert.*;

import org.junit.Test;

public class PaceTypeTest {

    /** PaceType will be persisted by name, so renaming a constant is a breaking change. */
    @Test
    public void namesAreStable() {
        assertEquals(PaceType.FIXED_SCHEDULE, PaceType.valueOf("FIXED_SCHEDULE"));
        assertEquals(PaceType.BY_END_DATE, PaceType.valueOf("BY_END_DATE"));
        assertEquals(PaceType.BY_DAILY_AMOUNT, PaceType.valueOf("BY_DAILY_AMOUNT"));
        assertEquals(3, PaceType.values().length);
    }
}
