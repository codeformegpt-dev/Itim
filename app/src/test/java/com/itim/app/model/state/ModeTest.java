package com.itim.app.model.state;

import static org.junit.Assert.*;

import org.junit.Test;

public class ModeTest {

    /** Mode is persisted by name (Progress, LaunchState), so renaming a constant is a breaking change. */
    @Test
    public void namesAreStable() {
        assertEquals(Mode.DAILY, Mode.valueOf("DAILY"));
        assertEquals(Mode.PERSONAL, Mode.valueOf("PERSONAL"));
        assertEquals(2, Mode.values().length);
    }
}
