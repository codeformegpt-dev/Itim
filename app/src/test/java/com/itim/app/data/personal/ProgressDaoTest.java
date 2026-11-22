package com.itim.app.data.personal;

import static com.itim.app.data.TestUtil.await;
import static com.itim.app.data.TestUtil.getValue;
import static org.junit.Assert.*;

import com.itim.app.model.state.Mode;
import com.itim.app.model.state.Progress;

import org.junit.Test;

import java.util.List;

public class ProgressDaoTest extends PersonalDatabaseTestBase {

    @Test
    public void getProgressMissingIsNull() {
        assertNull(await(db.progressDao().getProgress(Mode.DAILY, 1L)));
    }

    @Test
    public void upsertProgressInserts() {
        db.progressDao().upsertProgress(Mode.PERSONAL, 1L, 101L);
        Progress p = await(db.progressDao().getProgress(Mode.PERSONAL, 1L));
        assertNotNull(p);
        assertTrue(p.getId() > 0);
        assertEquals(Mode.PERSONAL, p.getMode());
        assertEquals(1L, p.getSourceId());
        assertEquals(101L, p.getComponentId());
    }

    @Test
    public void upsertProgressUpdatesSameRow() {
        db.progressDao().upsertProgress(Mode.PERSONAL, 1L, 101L);
        long id = await(db.progressDao().getProgress(Mode.PERSONAL, 1L)).getId();

        db.progressDao().upsertProgress(Mode.PERSONAL, 1L, 102L);
        Progress p = await(db.progressDao().getProgress(Mode.PERSONAL, 1L));
        assertEquals(new Progress(id, Mode.PERSONAL, 1L, 102L), p);
        assertEquals(1, await(db.progressDao().getProgressOfSource(1L)).size());
    }

    @Test
    public void progressIsSeparatedByModeAndSource() {
        db.progressDao().upsertProgress(Mode.PERSONAL, 1L, 101L);
        db.progressDao().upsertProgress(Mode.DAILY, 1L, 102L);
        db.progressDao().upsertProgress(Mode.PERSONAL, 2L, 201L);

        assertEquals(101L, await(db.progressDao().getProgress(Mode.PERSONAL, 1L)).getComponentId());
        assertEquals(102L, await(db.progressDao().getProgress(Mode.DAILY, 1L)).getComponentId());
        assertEquals(201L, await(db.progressDao().getProgress(Mode.PERSONAL, 2L)).getComponentId());
    }

    @Test
    public void getProgressOfSourceOrderedByMode() {
        db.progressDao().upsertProgress(Mode.PERSONAL, 1L, 101L);
        db.progressDao().upsertProgress(Mode.DAILY, 1L, 102L);
        db.progressDao().upsertProgress(Mode.DAILY, 2L, 201L);

        List<Progress> list = await(db.progressDao().getProgressOfSource(1L));
        assertEquals(2, list.size());
        assertEquals(Mode.DAILY, list.get(0).getMode());
        assertEquals(Mode.PERSONAL, list.get(1).getMode());
    }

    @Test
    public void observeProgress() {
        assertNull(getValue(db.progressDao().observeProgress(Mode.DAILY, 1L)));
        db.progressDao().upsertProgress(Mode.DAILY, 1L, 105L);
        assertEquals(105L, getValue(db.progressDao().observeProgress(Mode.DAILY, 1L)).getComponentId());
    }

    @Test
    public void deleteProgressOnlyDeletesGivenMode() {
        db.progressDao().upsertProgress(Mode.PERSONAL, 1L, 101L);
        db.progressDao().upsertProgress(Mode.DAILY, 1L, 102L);

        await(db.progressDao().deleteProgress(Mode.DAILY, 1L));
        assertNull(await(db.progressDao().getProgress(Mode.DAILY, 1L)));
        assertNotNull(await(db.progressDao().getProgress(Mode.PERSONAL, 1L)));
    }
}
