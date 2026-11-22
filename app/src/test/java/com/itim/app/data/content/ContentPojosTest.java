package com.itim.app.data.content;

import static org.junit.Assert.*;

import com.itim.app.model.content.Commentary;
import com.itim.app.model.content.SchedulePortion;
import com.itim.app.model.content.Source;
import com.itim.app.model.distribution.SourceDownload;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;

/** בדיקות לאובייקטי התוצאה של שאילתות ה-JOIN במסד התוכן. */
public class ContentPojosTest {

    private static final SchedulePortion PORTION = new SchedulePortion(1L, 2L, LocalDate.of(2026, 10, 18), 10L, 20L, "פרק א");

    // ---------- PortionWithBounds ----------

    @Test
    public void portionWithBoundsGetters() {
        PortionWithBounds p = new PortionWithBounds(PORTION, 3, "א", 7, "ה");
        assertEquals(PORTION, p.getPortion());
        assertEquals(3, p.getStartOrderIndex());
        assertEquals("א", p.getStartLabel());
        assertEquals(7, p.getEndOrderIndex());
        assertEquals("ה", p.getEndLabel());
    }

    @Test
    public void portionWithBoundsEquals() {
        PortionWithBounds a = new PortionWithBounds(PORTION, 3, "א", 7, "ה");
        assertEquals(a, new PortionWithBounds(PORTION, 3, "א", 7, "ה"));
        assertEquals(a.hashCode(), new PortionWithBounds(PORTION, 3, "א", 7, "ה").hashCode());
        assertNotEquals(a, new PortionWithBounds(PORTION, 4, "א", 7, "ה"));
        assertNotEquals(a, new PortionWithBounds(PORTION, 3, "ב", 7, "ה"));
        assertNotEquals(a, new PortionWithBounds(PORTION, 3, "א", 8, "ה"));
        assertNotEquals(a, new PortionWithBounds(PORTION, 3, "א", 7, "ו"));
        assertNotEquals(a, null);
    }

    // ---------- CommentaryWithCommentator ----------

    @Test
    public void commentaryWithCommentatorGetters() {
        Commentary c = new Commentary(1L, 2L, 3L, null, 0, "פירוש");
        CommentaryWithCommentator w = new CommentaryWithCommentator(c, "רש\"י");
        assertEquals(c, w.getCommentary());
        assertEquals("רש\"י", w.getCommentatorName());
    }

    @Test
    public void commentaryWithCommentatorEquals() {
        Commentary c = new Commentary(1L, 2L, 3L, null, 0, "פירוש");
        CommentaryWithCommentator a = new CommentaryWithCommentator(c, "רש\"י");
        assertEquals(a, new CommentaryWithCommentator(c, "רש\"י"));
        assertEquals(a.hashCode(), new CommentaryWithCommentator(c, "רש\"י").hashCode());
        assertNotEquals(a, new CommentaryWithCommentator(c, "רמב\"ן"));
        assertNotEquals(a, new CommentaryWithCommentator(new Commentary(9L, 2L, 3L, null, 0, "פירוש"), "רש\"י"));
        assertNotEquals(a, null);
    }

    // ---------- ComponentOutline ----------

    @Test
    public void componentOutlineGetters() {
        ComponentOutline o = new ComponentOutline(5L, 1L, 2, "פרק ב", false);
        assertEquals(5L, o.getId());
        assertEquals(Long.valueOf(1L), o.getParentId());
        assertEquals(2, o.getOrderIndex());
        assertEquals("פרק ב", o.getLabel());
        assertFalse(o.isLeaf());
        assertNull(new ComponentOutline(1L, null, 0, "שורש", true).getParentId());
    }

    @Test
    public void componentOutlineEquals() {
        ComponentOutline a = new ComponentOutline(5L, 1L, 2, "פרק ב", false);
        assertEquals(a, new ComponentOutline(5L, 1L, 2, "פרק ב", false));
        assertEquals(a.hashCode(), new ComponentOutline(5L, 1L, 2, "פרק ב", false).hashCode());
        assertNotEquals(a, new ComponentOutline(6L, 1L, 2, "פרק ב", false));
        assertNotEquals(a, new ComponentOutline(5L, null, 2, "פרק ב", false));
        assertNotEquals(a, new ComponentOutline(5L, 1L, 3, "פרק ב", false));
        assertNotEquals(a, new ComponentOutline(5L, 1L, 2, "פרק ג", false));
        assertNotEquals(a, new ComponentOutline(5L, 1L, 2, "פרק ב", true));
        assertNotEquals(a, null);
    }

    // ---------- SourceWithDownload ----------

    @Test
    public void sourceWithDownloadGetters() {
        Source s = new Source(1L, "בראשית");
        SourceDownload d = new SourceDownload(1L, 2, null, null, null, Instant.ofEpochMilli(1000L));
        SourceWithDownload w = new SourceWithDownload(s, d);
        assertEquals(s, w.getSource());
        assertEquals(d, w.getDownload());
        assertNull(new SourceWithDownload(s, null).getDownload());
    }

    @Test
    public void sourceWithDownloadEquals() {
        Source s = new Source(1L, "בראשית");
        SourceDownload d = new SourceDownload(1L, 2, null, null, null, null);
        SourceWithDownload a = new SourceWithDownload(s, d);
        assertEquals(a, new SourceWithDownload(s, d));
        assertEquals(a.hashCode(), new SourceWithDownload(s, d).hashCode());
        assertNotEquals(a, new SourceWithDownload(s, null));
        assertNotEquals(a, new SourceWithDownload(new Source(2L, "שמות"), d));
        assertNotEquals(a, null);
    }
}
