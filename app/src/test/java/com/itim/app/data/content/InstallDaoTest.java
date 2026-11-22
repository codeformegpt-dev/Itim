package com.itim.app.data.content;

import static com.itim.app.data.TestUtil.await;
import static org.junit.Assert.*;

import android.database.sqlite.SQLiteConstraintException;

import com.itim.app.model.content.Component;

import org.junit.Test;

import java.util.Collections;

public class InstallDaoTest extends ContentDatabaseTestBase {

    @Test
    public void deleteInstalledSourceRemovesEverythingOfSource() {
        db.installDao().deleteInstalledSource(SRC_INSTALLED);

        assertTrue(db.sourceDao().getInstalledSourceIds().isEmpty());
        assertNull(await(db.sourceDao().getSourceDownload(SRC_INSTALLED)));
        assertNull(await(db.componentDao().getComponent(LEAF_1A.getId())));
        assertTrue(await(db.commentaryDao().getCommentaries(LEAF_1A.getId())).isEmpty());
        assertTrue(await(db.scheduleDao().getSchedules(SRC_INSTALLED)).isEmpty());
        assertNull(await(db.scheduleDao().getPortion(10L, DAY_1)));
        assertNull(await(db.scheduleDao().getDefaultScheduleId(SRC_INSTALLED)));
        assertTrue(await(db.scheduleDao().getLevels(SRC_INSTALLED)).isEmpty());
        assertTrue(await(db.searchDao().getTextSearchPage("אור", null, 10, 0)).isEmpty());
    }

    @Test
    public void deleteInstalledSourceKeepsOtherSources() {
        db.installDao().deleteInstalledSource(SRC_INSTALLED);

        assertEquals(LEAF_2A, await(db.componentDao().getComponent(LEAF_2A.getId())));
        assertNotNull(await(db.sourceDao().getSourceDownload(SRC_DOWNLOADING)));
        assertEquals(Collections.singletonList(LEAF_2A),
                await(db.searchDao().getTextSearchPage("שמות", null, 10, 0)));
    }

    @Test
    public void updateInstalledVersionOnDownloadingSource() {
        db.installDao().updateInstalledVersion(SRC_DOWNLOADING);
        assertEquals(java.util.Arrays.asList(SRC_INSTALLED, SRC_DOWNLOADING), db.sourceDao().getInstalledSourceIds());
    }

    @Test(expected = SQLiteConstraintException.class)
    public void foreignKeysAreEnforced() {
        // רכיב שמפנה למקור שלא קיים
        db.installDao().insertComponents(Collections.singletonList(new Component(500L, 99L, null, 0, "x", null)));
    }

    @Test(expected = SQLiteConstraintException.class)
    public void componentOrderIndexIsUniquePerSource() {
        db.installDao().insertComponents(Collections.singletonList(new Component(500L, SRC_INSTALLED, null, 1, "כפול", "x")));
    }
}
