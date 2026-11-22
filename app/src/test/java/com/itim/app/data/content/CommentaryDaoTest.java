package com.itim.app.data.content;

import static com.itim.app.data.TestUtil.await;
import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Arrays;

public class CommentaryDaoTest extends ContentDatabaseTestBase {

    @Test
    public void getCommentariesOrderedWithCommentatorName() {
        assertEquals(Arrays.asList(
                        new CommentaryWithCommentator(RASHI_ON_1A, "רש\"י"),
                        new CommentaryWithCommentator(IBN_EZRA_ON_1A, "אבן עזרא")),
                await(db.commentaryDao().getCommentaries(101L)));
    }

    @Test
    public void getCommentariesEmpty() {
        assertTrue(await(db.commentaryDao().getCommentaries(102L)).isEmpty());
    }

    @Test
    public void getHasAdditionalCommentators() {
        assertTrue(await(db.commentaryDao().getHasAdditionalCommentators(SRC_INSTALLED)));
        assertFalse(await(db.commentaryDao().getHasAdditionalCommentators(SRC_DOWNLOADING)));
        assertFalse(await(db.commentaryDao().getHasAdditionalCommentators(99L)));
    }
}
