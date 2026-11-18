package com.itim.app.data.content;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Query;

import com.google.common.util.concurrent.ListenableFuture;
import com.itim.app.model.content.SourceLevel;
import com.itim.app.model.content.SourceSchedule;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface  ScheduleDao {


    @Query("SELECT p.scheduleId, p.date, p.startComponentId, p.endComponentId, p.title, sc.orderIndex AS startOrderIndex, sc.label AS startLabel, ec.orderIndex AS endOrderIndex, ec.label AS endLabel "
            + "FROM schedule_portions AS p "
            + "INNER JOIN components AS sc ON sc.id = p.startComponentId "
            + "INNER JOIN components AS ec ON ec.id = p.endComponentId "
            + "WHERE p.scheduleId = :scheduleId "
            + "AND p.date = :date")
    @NonNull
    ListenableFuture<PortionWithBounds> getPortion(long scheduleId, LocalDate date);

    @Query("SELECT date "
            + "FROM schedule_portions "
            + "WHERE scheduleId = :scheduleId "
            + "AND date BETWEEN :fromDate AND :toDate "
            + "ORDER BY date")
    @NonNull
    ListenableFuture<List<LocalDate>> getPortionDates(long scheduleId, LocalDate fromDate, LocalDate toDate);

    @Query("SELECT MIN(date) "
            + "FROM schedule_portions "
            + "WHERE scheduleId = :scheduleId")
    @NonNull
    ListenableFuture<LocalDate> getFirstPortionDate(long scheduleId);

    @Query("SELECT MAX(date) "
            + "FROM schedule_portions "
            + "WHERE scheduleId = :scheduleId")
    @NonNull
    ListenableFuture<LocalDate> getLastPortionDate(long scheduleId);

    @Query("SELECT * "
            + "FROM source_schedules "
            + "WHERE sourceId = :sourceId "
            + "ORDER BY id")
    @NonNull
    ListenableFuture<List<SourceSchedule>> getSchedules(long sourceId);

    @Query("SELECT * "
            + "FROM source_schedules "
            + "WHERE id = :scheduleId")
    @NonNull
    ListenableFuture<SourceSchedule> getSchedule(long scheduleId);

    @Query("SELECT sourceScheduleId "
            + "FROM default_source_schedules "
            + "WHERE sourceId = :sourceId")
    @NonNull
    ListenableFuture<Long> getDefaultScheduleId(long sourceId);

    @Query("SELECT * "
            + "FROM source_levels "
            + "WHERE sourceId = :sourceId "
            + "ORDER BY depth")
    @NonNull
    ListenableFuture<List<SourceLevel>> getLevels(long sourceId);


}
