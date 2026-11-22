package com.itim.app.data.content;

import static com.itim.app.data.TestUtil.await;
import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Arrays;

public class ScheduleDaoTest extends ContentDatabaseTestBase {

    @Test
    public void getPortionWithBounds() {
        assertEquals(new PortionWithBounds(PORTION_DAY_1, 1, "א", 2, "ב"),
                await(db.scheduleDao().getPortion(10L, DAY_1)));
    }

    @Test
    public void getPortionSameStartAndEnd() {
        assertEquals(new PortionWithBounds(PORTION_DAY_2, 2, "ב", 2, "ב"),
                await(db.scheduleDao().getPortion(10L, DAY_1.plusDays(1))));
    }

    @Test
    public void getPortionMissingIsNull() {
        assertNull(await(db.scheduleDao().getPortion(10L, DAY_1.plusDays(2))));
        assertNull(await(db.scheduleDao().getPortion(99L, DAY_1)));
    }

    @Test
    public void getPortionDatesIsInclusiveAndOrdered() {
        assertEquals(Arrays.asList(DAY_1, DAY_1.plusDays(1), DAY_1.plusDays(9)),
                await(db.scheduleDao().getPortionDates(10L, DAY_1, DAY_1.plusDays(9))));
        assertEquals(Arrays.asList(DAY_1, DAY_1.plusDays(1)),
                await(db.scheduleDao().getPortionDates(10L, DAY_1.minusDays(5), DAY_1.plusDays(8))));
    }

    @Test
    public void getFirstAndLastPortionDate() {
        assertEquals(DAY_1, await(db.scheduleDao().getFirstPortionDate(10L)));
        assertEquals(DAY_1.plusDays(9), await(db.scheduleDao().getLastPortionDate(10L)));
        assertNull(await(db.scheduleDao().getFirstPortionDate(99L)));
        assertNull(await(db.scheduleDao().getLastPortionDate(99L)));
    }

    @Test
    public void getSchedules() {
        assertEquals(Arrays.asList(SCHEDULE_REGULAR, SCHEDULE_FAST), await(db.scheduleDao().getSchedules(SRC_INSTALLED)));
        assertTrue(await(db.scheduleDao().getSchedules(SRC_DOWNLOADING)).isEmpty());
    }

    @Test
    public void getSchedule() {
        assertEquals(SCHEDULE_FAST, await(db.scheduleDao().getSchedule(11L)));
        assertNull(await(db.scheduleDao().getSchedule(99L)));
    }

    @Test
    public void getDefaultScheduleId() {
        assertEquals(Long.valueOf(10L), await(db.scheduleDao().getDefaultScheduleId(SRC_INSTALLED)));
        assertNull(await(db.scheduleDao().getDefaultScheduleId(SRC_DOWNLOADING)));
    }

    @Test
    public void getLevelsOrderedByDepth() {
        assertEquals(Arrays.asList(LEVEL_BOOK, LEVEL_CHAPTER), await(db.scheduleDao().getLevels(SRC_INSTALLED)));
    }
}
