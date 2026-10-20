package com.itim.app.model.content;

import static org.junit.Assert.*;

import org.junit.Test;

public class ComponentTest {

    private static Component sample() {
        return new Component(5L, 1L, 2L, 7, "פרק א", "בראשית ברא");
    }

    @Test
    public void getters() {
        Component c = sample();
        assertEquals(5L, c.getId());
        assertEquals(1L, c.getSourceId());
        assertEquals(Long.valueOf(2L), c.getParentId());
        assertEquals(7, c.getOrderIndex());
        assertEquals("פרק א", c.getLabel());
        assertEquals("בראשית ברא", c.getText());
    }

    @Test
    public void rootComponentHasNoParentAndNoText() {
        Component root = new Component(1L, 1L, null, 0, "ספר", null);
        assertNull(root.getParentId());
        assertNull(root.getText());
    }

    @Test
    public void testEquals() {
        assertEquals(sample(), sample());
        // parentId is a boxed Long; values outside the Long cache must still compare equal
        assertEquals(new Component(5L, 1L, 1000L, 7, "א", null), new Component(5L, 1L, 1000L, 7, "א", null));
        assertNotEquals(sample(), new Component(6L, 1L, 2L, 7, "פרק א", "בראשית ברא"));
        assertNotEquals(sample(), new Component(5L, 9L, 2L, 7, "פרק א", "בראשית ברא"));
        assertNotEquals(sample(), new Component(5L, 1L, null, 7, "פרק א", "בראשית ברא"));
        assertNotEquals(sample(), new Component(5L, 1L, 2L, 8, "פרק א", "בראשית ברא"));
        assertNotEquals(sample(), new Component(5L, 1L, 2L, 7, "פרק ב", "בראשית ברא"));
        assertNotEquals(sample(), new Component(5L, 1L, 2L, 7, "פרק א", null));
        assertNotEquals(sample(), null);
    }

    @Test
    public void testHashCode() {
        assertEquals(sample().hashCode(), sample().hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("Component{id=5, sourceId=1, parentId=2, orderIndex=7, label='פרק א', text='בראשית ברא'}",
                sample().toString());
    }
}
