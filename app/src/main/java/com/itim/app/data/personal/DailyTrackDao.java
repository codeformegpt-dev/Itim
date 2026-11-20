package com.itim.app.data.personal;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.google.common.util.concurrent.ListenableFuture;
import com.itim.app.model.state.DailyAmountPace;
import com.itim.app.model.state.DailySchedule;
import com.itim.app.model.state.DailyScheduleWithPace;
import com.itim.app.model.state.EndDatePace;
import com.itim.app.model.state.FixedPace;

import java.time.LocalDate;

@Dao
public abstract class DailyTrackDao {

    @Query("SELECT * "
            + "FROM daily_schedules "
            + "WHERE sourceId = :sourceId "
            + "ORDER BY id "
            + "DESC LIMIT 1")
    public abstract ListenableFuture<DailyScheduleWithPace> getDailySchedule(long sourceId);

    @Query("SELECT * "
            + "FROM daily_schedules "
            + "WHERE sourceId = :sourceId "
            + "ORDER BY id "
            + "DESC LIMIT 1")
    public abstract LiveData<DailyScheduleWithPace> observeDailySchedule(long sourceId);

    @Query("DELETE FROM daily_schedules "
            + "WHERE sourceId = :sourceId "
            + "AND id < (SELECT MAX(id) FROM daily_schedules WHERE sourceId = :sourceId)")
    public abstract ListenableFuture<Void> deleteDuplicateDailySchedules(long sourceId);

    @Query("DELETE FROM daily_schedules "
            + "WHERE sourceId = :sourceId")
    public abstract ListenableFuture<Void> deleteDailySchedule(long sourceId);

    //טרנזקציות

    @Transaction
    public void setLearningDate(long sourceId, int offsetDays, @Nullable Long defaultScheduleId) {
        Long id = getLatestDailyScheduleId(sourceId);
        if (id != null)
            updateOffsetDays(id, offsetDays);
        else
            if (defaultScheduleId == null)
                throw new IllegalArgumentException(); //TODO לכתוב כאן הערת שגיאה מתאימה
            long newId = insertDailySchedule(new DailySchedule(0, sourceId, offsetDays, null));
            insertFixedPace(new FixedPace(newId, defaultScheduleId));
        deleteDailyProgress(sourceId);
    }

    @Transaction
    public void markCompleted(long sourceId, LocalDate date, @Nullable Long defaultScheduleId) {
        Long id = getLatestDailyScheduleId(sourceId);
        if (id != null)
            updateLastCompletedAt(id, date);
        else
        if (defaultScheduleId == null)
            throw new IllegalArgumentException(); //TODO לכתוב כאן הערת שגיאה מתאימה
        long newId = insertDailySchedule(new DailySchedule(0, sourceId, 0, date));
        insertFixedPace(new FixedPace(newId,defaultScheduleId));
    }

    @Transaction
    public void switchToFixedPace(long sourceId, long sourceScheduleId) {
        long id = prepareRowForNewPace(sourceId);
        insertFixedPace(new FixedPace(id, sourceScheduleId));
    }

    @Transaction
    public void switchToEndDatePace(long sourceId, LocalDate startDate, LocalDate targetEndDate) {
        long id = prepareRowForNewPace(sourceId);
        insertEndDatePace(new EndDatePace(id, startDate, targetEndDate));
    }

    @Transaction
    public void switchToDailyAmountPace(long sourceId, LocalDate startDate, int unitDepth, int unitPerDay) {
        long id = prepareRowForNewPace(sourceId);
        insertDailyAmountPace(new DailyAmountPace(id, startDate, unitDepth, unitPerDay));
    }


    @Query("SELECT id "
            + "FROM daily_schedules "
            + "WHERE sourceId = :sourceId "
            + "ORDER BY id "
            + "DESC LIMIT 1")
    @Nullable
    abstract Long getLatestDailyScheduleId(long sourceId);

    @Insert
    abstract long insertDailySchedule(DailySchedule row);

    @Insert
    abstract void insertEndDatePace(EndDatePace pace);

    @Insert
    abstract void insertFixedPace(FixedPace pace);

    @Insert
    abstract void insertDailyAmountPace(DailyAmountPace pace);

    @Query("DELETE FROM fixed_paces " +
            "WHERE dailyScheduleId = :dailyScheduleId")
    abstract void deleteFixedPace(long dailyScheduleId);

    @Query("DELETE FROM end_date_paces " +
            "WHERE dailyScheduleId = :dailyScheduleId")
    abstract void deleteEndDatePace(long dailyScheduleId);

    @Query("DELETE FROM daily_amount_paces " +
            "WHERE dailyScheduleId = :dailyScheduleId")
    abstract void deleteDailyAmountPace(long dailyScheduleId);

    @Query("UPDATE daily_schedules " +
            "SET offsetDays = :offsetDays, lastCompletedDate = NULL " +
            "WHERE id = :dailySchedules")
    abstract void updateOffsetDays(long dailySchedules, int offsetDays);

    @Query("UPDATE daily_schedules " +
            "SET lastCompletedDate = :date " +
            "WHERE id = :dailyScheduleId " +
            "AND lastCompletedDate IS NULL " +
            "OR lastCompletedDate < :date")
    abstract void updateLastCompletedAt(long dailyScheduleId, LocalDate date);

    @Query("DELETE FROM progress " +
            "WHERE mode = 'DAILY' " +
            "AND sourceId = :sourceId")
    abstract void deleteDailyProgress(long sourceId);

    private long prepareRowForNewPace(long sourceId) {
        Long id = getLatestDailyScheduleId(sourceId);

        if (id == null)
            id = insertDailySchedule(new DailySchedule(0, sourceId,0, null));
        else
            deleteFixedPace(id);
            deleteEndDatePace(id);
            deleteDailyAmountPace(id);
            updateOffsetDays(id, 0);

        deleteDailyProgress(sourceId);
        return id;
    }


























}
