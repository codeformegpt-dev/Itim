package com.itim.app.data.personal;

import static com.itim.app.data.TestUtil.await;
import static com.itim.app.data.TestUtil.getValue;
import static org.junit.Assert.*;

import com.itim.app.model.state.DailyAmountPace;
import com.itim.app.model.state.DailySchedule;
import com.itim.app.model.state.EndDatePace;
import com.itim.app.model.state.FixedPace;
import com.itim.app.model.state.Mode;

import org.junit.Test;

import java.time.LocalDate;

public class DailyTrackDaoTest extends PersonalDatabaseTestBase {

    private static final long SOURCE = 1L;
    private static final long OTHER_SOURCE = 2L;
    private static final long DEFAULT_SCHEDULE = 10L;
    private static final LocalDate DAY = LocalDate.of(2026, 10, 18);

    private DailyTrackDao dao() {
        return db.dailyTrackDao();
    }

    private DailyScheduleWithPace current(long sourceId) {
        return await(dao().getDailySchedule(sourceId));
    }

    private boolean hasDailyProgress(long sourceId) {
        return await(db.progressDao().getProgress(Mode.DAILY, sourceId)) != null;
    }

    // ---------- קריאה ----------

    @Test
    public void getDailyScheduleMissingIsNull() {
        assertNull(current(SOURCE));
        assertNull(getValue(dao().observeDailySchedule(SOURCE)));
    }

    // ---------- setLearningDate ----------

    @Test
    public void setLearningDateWithoutRowOrDefaultThrows() {
        assertThrows(IllegalStateException.class, () -> dao().setLearningDate(SOURCE, 3, null));
        assertNull(current(SOURCE));
    }

    @Test
    public void setLearningDateCreatesRowWithDefaultFixedPace() {
        dao().setLearningDate(SOURCE, 3, DEFAULT_SCHEDULE);

        DailyScheduleWithPace w = current(SOURCE);
        assertNotNull(w);
        long id = w.getDailySchedule().getId();
        assertEquals(new DailySchedule(id, SOURCE, 3, null), w.getDailySchedule());
        assertEquals(new FixedPace(id, DEFAULT_SCHEDULE), w.getPace());
    }

    @Test
    public void setLearningDateUpdatesExistingRowAndClearsCompletion() {
        dao().markCompleted(SOURCE, DAY, DEFAULT_SCHEDULE);
        db.progressDao().upsertProgress(Mode.DAILY, SOURCE, 101L);
        db.progressDao().upsertProgress(Mode.PERSONAL, SOURCE, 102L);
        long id = current(SOURCE).getDailySchedule().getId();

        dao().setLearningDate(SOURCE, -2, null); // ברירת מחדל לא נדרשת כשיש שורה

        DailyScheduleWithPace w = current(SOURCE);
        assertEquals(new DailySchedule(id, SOURCE, -2, null), w.getDailySchedule());
        assertEquals(new FixedPace(id, DEFAULT_SCHEDULE), w.getPace());
        assertFalse(hasDailyProgress(SOURCE));
        assertNotNull(await(db.progressDao().getProgress(Mode.PERSONAL, SOURCE)));
    }

    // ---------- markCompleted ----------

    @Test
    public void markCompletedWithoutRowOrDefaultThrows() {
        assertThrows(IllegalStateException.class, () -> dao().markCompleted(SOURCE, DAY, null));
        assertNull(current(SOURCE));
    }

    @Test
    public void markCompletedCreatesRowWithDefaultFixedPace() {
        dao().markCompleted(SOURCE, DAY, DEFAULT_SCHEDULE);

        DailyScheduleWithPace w = current(SOURCE);
        long id = w.getDailySchedule().getId();
        assertEquals(new DailySchedule(id, SOURCE, 0, DAY), w.getDailySchedule());
        assertEquals(new FixedPace(id, DEFAULT_SCHEDULE), w.getPace());
    }

    @Test
    public void markCompletedMovesForward() {
        dao().markCompleted(SOURCE, DAY, DEFAULT_SCHEDULE);
        dao().markCompleted(SOURCE, DAY.plusDays(1), null);
        assertEquals(DAY.plusDays(1), current(SOURCE).getDailySchedule().getLastCompletedDate());
    }

    @Test
    public void markCompletedNeverMovesBackward() {
        dao().markCompleted(SOURCE, DAY, DEFAULT_SCHEDULE);
        dao().markCompleted(SOURCE, DAY.minusDays(1), null);
        assertEquals(DAY, current(SOURCE).getDailySchedule().getLastCompletedDate());
    }

    @Test
    public void markCompletedOnRowWithoutCompletion() {
        dao().setLearningDate(SOURCE, 0, DEFAULT_SCHEDULE);
        dao().markCompleted(SOURCE, DAY, null);
        assertEquals(DAY, current(SOURCE).getDailySchedule().getLastCompletedDate());
    }

    @Test
    public void markCompletedDoesNotAffectOtherSource() {
        dao().markCompleted(SOURCE, DAY, DEFAULT_SCHEDULE);
        dao().markCompleted(OTHER_SOURCE, DAY.minusDays(3), DEFAULT_SCHEDULE);
        dao().markCompleted(OTHER_SOURCE, DAY.plusDays(3), null);
        assertEquals(DAY, current(SOURCE).getDailySchedule().getLastCompletedDate());
        assertEquals(DAY.plusDays(3), current(OTHER_SOURCE).getDailySchedule().getLastCompletedDate());
    }

    // ---------- החלפת קצב ----------

    @Test
    public void switchToEndDatePaceReplacesFixedPaceAndResets() {
        dao().setLearningDate(SOURCE, 4, DEFAULT_SCHEDULE);
        dao().markCompleted(SOURCE, DAY, null);
        db.progressDao().upsertProgress(Mode.DAILY, SOURCE, 101L);
        long id = current(SOURCE).getDailySchedule().getId();

        dao().switchToEndDatePace(SOURCE, DAY, DAY.plusMonths(6));

        DailyScheduleWithPace w = current(SOURCE);
        assertEquals(new DailySchedule(id, SOURCE, 0, null), w.getDailySchedule());
        assertNull(w.getFixedPace());
        assertEquals(new EndDatePace(id, DAY, DAY.plusMonths(6)), w.getPace());
        assertFalse(hasDailyProgress(SOURCE));
    }

    @Test
    public void switchToDailyAmountPaceReplacesEndDatePace() {
        dao().switchToEndDatePace(SOURCE, DAY, DAY.plusMonths(6));
        long id = current(SOURCE).getDailySchedule().getId();

        dao().switchToDailyAmountPace(SOURCE, DAY, 2, 3);

        DailyScheduleWithPace w = current(SOURCE);
        assertEquals(id, w.getDailySchedule().getId());
        assertNull(w.getEndDatePace());
        assertEquals(new DailyAmountPace(id, DAY, 2, 3), w.getPace());
    }

    @Test
    public void switchToFixedPaceReplacesDailyAmountPace() {
        dao().switchToDailyAmountPace(SOURCE, DAY, 2, 3);
        long id = current(SOURCE).getDailySchedule().getId();

        dao().switchToFixedPace(SOURCE, 11L);

        DailyScheduleWithPace w = current(SOURCE);
        assertNull(w.getDailyAmountPace());
        assertEquals(new FixedPace(id, 11L), w.getPace());
    }

    @Test
    public void switchPaceWithoutRowCreatesRow() {
        dao().switchToFixedPace(SOURCE, 11L);
        DailyScheduleWithPace w = current(SOURCE);
        assertNotNull(w);
        long id = w.getDailySchedule().getId();
        assertEquals(new DailySchedule(id, SOURCE, 0, null), w.getDailySchedule());
        assertEquals(new FixedPace(id, 11L), w.getPace());
    }

    @Test
    public void switchPaceDeletesDailyProgressOnlyOfThatSource() {
        db.progressDao().upsertProgress(Mode.DAILY, SOURCE, 101L);
        db.progressDao().upsertProgress(Mode.DAILY, OTHER_SOURCE, 201L);

        dao().switchToFixedPace(SOURCE, 11L);

        assertFalse(hasDailyProgress(SOURCE));
        assertTrue(hasDailyProgress(OTHER_SOURCE));
    }

    // ---------- מחיקה ----------

    @Test
    public void deleteDuplicateDailySchedulesKeepsLatest() {
        long first = dao().insertDailySchedule(new DailySchedule(0, SOURCE, 1, null));
        long second = dao().insertDailySchedule(new DailySchedule(0, SOURCE, 2, null));
        long other = dao().insertDailySchedule(new DailySchedule(0, OTHER_SOURCE, 5, null));
        dao().insertFixedPace(new FixedPace(first, 10L));
        dao().insertFixedPace(new FixedPace(second, 11L));

        await(dao().deleteDuplicateDailySchedules(SOURCE));

        DailyScheduleWithPace w = current(SOURCE);
        assertEquals(second, w.getDailySchedule().getId());
        assertEquals(new FixedPace(second, 11L), w.getPace());
        assertEquals(Long.valueOf(second), dao().getLatestDailyScheduleId(SOURCE));
        assertEquals(other, current(OTHER_SOURCE).getDailySchedule().getId());
    }

    @Test
    public void getDailyScheduleReturnsLatestRow() {
        dao().insertDailySchedule(new DailySchedule(0, SOURCE, 1, null));
        long latest = dao().insertDailySchedule(new DailySchedule(0, SOURCE, 2, null));
        assertEquals(latest, current(SOURCE).getDailySchedule().getId());
        assertEquals(2, getValue(dao().observeDailySchedule(SOURCE)).getDailySchedule().getOffsetDays());
    }

    @Test
    public void deleteDailyScheduleCascadesToPace() {
        dao().switchToEndDatePace(SOURCE, DAY, DAY.plusDays(30));
        long id = current(SOURCE).getDailySchedule().getId();

        await(dao().deleteDailySchedule(SOURCE));

        assertNull(current(SOURCE));
        // אם ה-CASCADE לא עבד, הכנסת קצב חדש לאותו מזהה הייתה נכשלת על מפתח ראשי כפול
        long newId = dao().insertDailySchedule(new DailySchedule(id, SOURCE, 0, null));
        dao().insertEndDatePace(new EndDatePace(newId, DAY, DAY.plusDays(1)));
        assertEquals(new EndDatePace(id, DAY, DAY.plusDays(1)), current(SOURCE).getPace());
    }
}
