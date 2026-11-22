package com.itim.app.data.personal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.google.common.util.concurrent.ListenableFuture;
import com.itim.app.model.state.DailyAmountPace;
import com.itim.app.model.state.DailySchedule;
import com.itim.app.model.state.EndDatePace;
import com.itim.app.model.state.FixedPace;

import java.time.LocalDate;

@Dao
public abstract class DailyTrackDao {

    @Transaction
    @Query("SELECT * "
            + "FROM daily_schedules "
            + "WHERE sourceId = :sourceId "
            + "ORDER BY id DESC "
            + "LIMIT 1")
    @NonNull
    public abstract ListenableFuture<DailyScheduleWithPace> getDailySchedule(long sourceId);

    @Transaction
    @Query("SELECT * "
            + "FROM daily_schedules "
            + "WHERE sourceId = :sourceId "
            + "ORDER BY id DESC "
            + "LIMIT 1")
    @NonNull
    public abstract LiveData<DailyScheduleWithPace> observeDailySchedule(long sourceId);

    @Query("DELETE FROM daily_schedules "
            + "WHERE sourceId = :sourceId "
            + "AND id < (SELECT MAX(id) FROM daily_schedules WHERE sourceId = :sourceId)")
    @NonNull
    public abstract ListenableFuture<Void> deleteDuplicateDailySchedules(long sourceId);

    @Query("DELETE FROM daily_schedules "
            + "WHERE sourceId = :sourceId")
    @NonNull
    public abstract ListenableFuture<Void> deleteDailySchedule(long sourceId);

    @Transaction
    public void setLearningDate(long sourceId, int offsetDays, @Nullable Long defaultScheduleId) {
        Long id = getLatestDailyScheduleId(sourceId);
        if (id != null) {
            updateOffsetDays(id, offsetDays);
        } else {
            if (defaultScheduleId == null) {
                throw new IllegalStateException("No DailySchedule row and no default schedule for sourceId " + sourceId);
            }
            long newId = insertDailySchedule(new DailySchedule(0, sourceId, offsetDays, null));
            insertFixedPace(new FixedPace(newId, defaultScheduleId));
        }
        deleteDailyProgress(sourceId);
    }

    @Transaction
    public void markCompleted(long sourceId, @NonNull LocalDate date, @Nullable Long defaultScheduleId) {
        Long id = getLatestDailyScheduleId(sourceId);
        if (id != null) {
            updateLastCompletedDate(id, date);
        } else {
            if (defaultScheduleId == null) {
                throw new IllegalStateException("No DailySchedule row and no default schedule for sourceId " + sourceId);
            }
            long newId = insertDailySchedule(new DailySchedule(0, sourceId, 0, date));
            insertFixedPace(new FixedPace(newId, defaultScheduleId));
        }
    }

    @Transaction
    public void switchToFixedPace(long sourceId, long sourceScheduleId) {
        long id = prepareRowForNewPace(sourceId);
        insertFixedPace(new FixedPace(id, sourceScheduleId));
    }

    @Transaction
    public void switchToEndDatePace(long sourceId, @NonNull LocalDate startDate, @NonNull LocalDate targetEndDate) {
        long id = prepareRowForNewPace(sourceId);
        insertEndDatePace(new EndDatePace(id, startDate, targetEndDate));
    }

    @Transaction
    public void switchToDailyAmountPace(long sourceId, @NonNull LocalDate startDate, int unitDepth, int unitsPerDay) {
        long id = prepareRowForNewPace(sourceId);
        insertDailyAmountPace(new DailyAmountPace(id, startDate, unitDepth, unitsPerDay));
    }

    private long prepareRowForNewPace(long sourceId) {
        Long id = getLatestDailyScheduleId(sourceId);
        if (id == null) {
            id = insertDailySchedule(new DailySchedule(0, sourceId, 0, null));
        } else {
            deleteFixedPace(id);
            deleteEndDatePace(id);
            deleteDailyAmountPace(id);
            updateOffsetDays(id, 0);
        }
        deleteDailyProgress(sourceId);
        return id;
    }

    @Query("SELECT id "
            + "FROM daily_schedules "
            + "WHERE sourceId = :sourceId "
            + "ORDER BY id DESC "
            + "LIMIT 1")
    @Nullable
    abstract Long getLatestDailyScheduleId(long sourceId);

    @Insert
    abstract long insertDailySchedule(@NonNull DailySchedule row);

    @Insert
    abstract void insertFixedPace(@NonNull FixedPace pace);

    @Insert
    abstract void insertEndDatePace(@NonNull EndDatePace pace);

    @Insert
    abstract void insertDailyAmountPace(@NonNull DailyAmountPace pace);

    @Query("DELETE FROM fixed_paces "
            + "WHERE dailyScheduleId = :dailyScheduleId")
    abstract void deleteFixedPace(long dailyScheduleId);

    @Query("DELETE FROM end_date_paces "
            + "WHERE dailyScheduleId = :dailyScheduleId")
    abstract void deleteEndDatePace(long dailyScheduleId);

    @Query("DELETE FROM daily_amount_paces "
            + "WHERE dailyScheduleId = :dailyScheduleId")
    abstract void deleteDailyAmountPace(long dailyScheduleId);

    @Query("UPDATE daily_schedules "
            + "SET offsetDays = :offsetDays, lastCompletedDate = NULL "
            + "WHERE id = :dailyScheduleId")
    abstract void updateOffsetDays(long dailyScheduleId, int offsetDays);

    @Query("UPDATE daily_schedules "
            + "SET lastCompletedDate = :date "
            + "WHERE id = :dailyScheduleId "
            + "AND (lastCompletedDate IS NULL OR lastCompletedDate < :date)")
    abstract void updateLastCompletedDate(long dailyScheduleId, @NonNull LocalDate date);

    @Query("DELETE FROM progress "
            + "WHERE mode = 'DAILY' "
            + "AND sourceId = :sourceId")
    abstract void deleteDailyProgress(long sourceId);
}