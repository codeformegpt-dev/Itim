package com.itim.app.data.content;

import static com.itim.app.data.TestUtil.await;
import static org.junit.Assert.*;

import com.itim.app.model.content.Source;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class SearchDaoTest extends ContentDatabaseTestBase {

    @Test
    public void textSearchFindsMatchingComponent() {
        assertEquals(Collections.singletonList(LEAF_1A),
                await(db.searchDao().getTextSearchPage("בראשית", null, 10, 0)));
    }

    @Test
    public void textSearchNoMatch() {
        assertTrue(await(db.searchDao().getTextSearchPage("ויקרא", null, 10, 0)).isEmpty());
    }

    @Test
    public void textSearchAllSourcesWhenSourceIdNull() {
        assertEquals(Collections.singletonList(LEAF_2A),
                await(db.searchDao().getTextSearchPage("שמות", null, 10, 0)));
    }

    @Test
    public void textSearchFilteredBySource() {
        assertTrue(await(db.searchDao().getTextSearchPage("שמות", SRC_INSTALLED, 10, 0)).isEmpty());
        assertEquals(Collections.singletonList(LEAF_2A),
                await(db.searchDao().getTextSearchPage("שמות", SRC_DOWNLOADING, 10, 0)));
    }

    @Test
    public void textSearchPaging() {
        assertEquals(Arrays.asList(LEAF_1A, LEAF_1B), await(db.searchDao().getTextSearchPage("אור", null, 10, 0)));
        assertEquals(Collections.singletonList(LEAF_1A), await(db.searchDao().getTextSearchPage("אור", null, 1, 0)));
        assertEquals(Collections.singletonList(LEAF_1B), await(db.searchDao().getTextSearchPage("אור", null, 1, 1)));
        assertTrue(await(db.searchDao().getTextSearchPage("אור", null, 1, 2)).isEmpty());
    }

    @Test
    public void offlineSearchableSourcesOnlyInstalledAndIndexed() {
        assertEquals(Collections.singletonList(new Source(SRC_INSTALLED, "בראשית")),
                await(db.searchDao().getOfflineSearchableSources()));
    }

    @Test
    public void offlineSearchableSourcesEmptyWithoutIndex() {
        db.installDao().deleteSearchIndexOfSource(SRC_INSTALLED);
        assertTrue(await(db.searchDao().getOfflineSearchableSources()).isEmpty());
    }
}
