package com.itim.app.data.content;

import static com.itim.app.data.TestUtil.await;
import static com.itim.app.data.TestUtil.getValue;
import static org.junit.Assert.*;

import com.itim.app.model.content.Source;
import com.itim.app.model.distribution.SourceDownload;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SourceDaoTest extends ContentDatabaseTestBase {

    @Test
    public void getInstalledSourceIds() {
        assertEquals(Collections.singletonList(SRC_INSTALLED), db.sourceDao().getInstalledSourceIds());
    }

    @Test
    public void observeInstalledSources() {
        assertEquals(Collections.singletonList(new Source(SRC_INSTALLED, "בראשית")),
                getValue(db.sourceDao().observeInstalledSources()));
    }

    @Test
    public void startFirstDownloadCreatesSourceAndDownload() {
        SourceDownload d = await(db.sourceDao().getSourceDownload(SRC_DOWNLOADING));
        assertEquals(new SourceDownload(SRC_DOWNLOADING, null, 1, 2, 0, null), d);
    }

    @Test
    public void updateInstalledVersionMovesTargetToInstalled() {
        SourceDownload d = await(db.sourceDao().getSourceDownload(SRC_INSTALLED));
        assertEquals(new SourceDownload(SRC_INSTALLED, 1, null, null, null, null), d);
    }

    @Test
    public void startFirstDownloadOnExistingSourceReplacesDownload() {
        db.sourceDao().startFirstDownload(SRC_DOWNLOADING, "שמות", 2, 5);
        assertEquals(new SourceDownload(SRC_DOWNLOADING, null, 2, 5, 0, null),
                await(db.sourceDao().getSourceDownload(SRC_DOWNLOADING)));
    }

    @Test
    public void getSourceDownloadMissingIsNull() {
        assertNull(await(db.sourceDao().getSourceDownload(99L)));
    }

    @Test
    public void observeSourceDownload() {
        assertEquals(new SourceDownload(SRC_DOWNLOADING, null, 1, 2, 0, null),
                getValue(db.sourceDao().observeSourceDownload(SRC_DOWNLOADING)));
    }

    @Test
    public void getSourcesWithDownload() {
        db.sourceDao().upsertSource(new Source(3L, "ויקרא")); // מקור בלי שורת הורדה
        List<SourceWithDownload> expected = Arrays.asList(
                new SourceWithDownload(new Source(SRC_INSTALLED, "בראשית"), new SourceDownload(SRC_INSTALLED, 1, null, null, null, null)),
                new SourceWithDownload(new Source(SRC_DOWNLOADING, "שמות"), new SourceDownload(SRC_DOWNLOADING, null, 1, 2, 0, null)),
                new SourceWithDownload(new Source(3L, "ויקרא"), null));
        assertEquals(expected, await(db.sourceDao().getSourcesWithDownload()));
        assertEquals(expected, getValue(db.sourceDao().observeSourcesWithDownload()));
    }

    @Test
    public void updateCompletedParts() {
        await(db.sourceDao().updateCompletedParts(SRC_DOWNLOADING, 1));
        assertEquals(Integer.valueOf(1), await(db.sourceDao().getSourceDownload(SRC_DOWNLOADING)).getCompletedParts());
    }

    @Test
    public void deleteSourceIfNotInstalledDeletesDownloadingSource() {
        await(db.sourceDao().deleteSourceIfNotInstalled(SRC_DOWNLOADING));
        assertNull(await(db.sourceDao().getSourceDownload(SRC_DOWNLOADING)));
        assertEquals(1, await(db.sourceDao().getSourcesWithDownload()).size());
        // CASCADE: הרכיבים של המקור נמחקו
        assertNull(await(db.componentDao().getComponent(LEAF_2A.getId())));
    }

    @Test
    public void deleteSourceIfNotInstalledKeepsInstalledSource() {
        await(db.sourceDao().deleteSourceIfNotInstalled(SRC_INSTALLED));
        assertEquals(Collections.singletonList(SRC_INSTALLED), db.sourceDao().getInstalledSourceIds());
        assertNotNull(await(db.componentDao().getComponent(LEAF_1A.getId())));
    }
}
