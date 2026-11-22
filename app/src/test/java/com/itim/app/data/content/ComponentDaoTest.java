package com.itim.app.data.content;

import static com.itim.app.data.TestUtil.await;
import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class ComponentDaoTest extends ContentDatabaseTestBase {

    @Test
    public void getComponent() {
        assertEquals(LEAF_1A, await(db.componentDao().getComponent(101L)));
        assertNull(await(db.componentDao().getComponent(999L)));
    }

    @Test
    public void getComponentRangeIsInclusiveAndOrdered() {
        assertEquals(Arrays.asList(LEAF_1A, LEAF_1B), await(db.componentDao().getComponentRange(SRC_INSTALLED, 1, 2)));
        assertEquals(Arrays.asList(ROOT_1, LEAF_1A), await(db.componentDao().getComponentRange(SRC_INSTALLED, 0, 1)));
    }

    @Test
    public void getComponentRangeIsScopedToSource() {
        assertEquals(Collections.singletonList(LEAF_2A), await(db.componentDao().getComponentRange(SRC_DOWNLOADING, 1, 5)));
    }

    @Test
    public void getComponentRangeEmpty() {
        assertTrue(await(db.componentDao().getComponentRange(SRC_INSTALLED, 5, 9)).isEmpty());
    }

    @Test
    public void getNonLeafComponents() {
        assertEquals(Collections.singletonList(ROOT_1), await(db.componentDao().getNonLeafComponents(SRC_INSTALLED)));
    }

    @Test
    public void getFirstLeafFrom() {
        assertEquals(LEAF_1A, await(db.componentDao().getFirstLeafFrom(SRC_INSTALLED, 0)));
        assertEquals(LEAF_1A, await(db.componentDao().getFirstLeafFrom(SRC_INSTALLED, 1)));
        assertEquals(LEAF_1B, await(db.componentDao().getFirstLeafFrom(SRC_INSTALLED, 2)));
        assertNull(await(db.componentDao().getFirstLeafFrom(SRC_INSTALLED, 3)));
    }

    @Test
    public void getInstalledNonLeafComponentsIgnoresSourcesNotInstalled() {
        assertEquals(Collections.singletonList(ROOT_1), await(db.componentDao().getInstalledNonLeafComponents()));
    }

    @Test
    public void getComponentOutline() {
        assertEquals(Arrays.asList(
                        new ComponentOutline(100L, null, 0, "בראשית", false),
                        new ComponentOutline(101L, 100L, 1, "א", true),
                        new ComponentOutline(102L, 100L, 2, "ב", true)),
                await(db.componentDao().getComponentOutline(SRC_INSTALLED)));
    }
}
