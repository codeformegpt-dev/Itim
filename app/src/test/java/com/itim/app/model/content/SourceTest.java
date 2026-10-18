package com.itim.app.model.content;

import static org.junit.Assert.*;

import org.junit.Test;

public class SourceTest {

    @Test
    public void getters() {
        Source s = new Source(3L, "תורה");
        assertEquals(3L, s.getId());
        assertEquals("תורה", s.getLabel());
    }
}
