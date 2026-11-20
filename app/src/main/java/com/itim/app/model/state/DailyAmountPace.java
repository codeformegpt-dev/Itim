package com.itim.app.model.state;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Objects;

@Entity(
        tableName = "daily_amount_paces",
        foreignKeys = @ForeignKey(
                entity = DailySchedule.class,
                parentColumns = "id",
                childColumns = "dailyScheduleId",
                onDelete = ForeignKey.CASCADE
        )
)
public final class DailyAmountPace implements StudyPace {

    @PrimaryKey
    private final long dailyScheduleId;
    @NonNull
    private final LocalDate startDate;
    private final int unitDepth;
    private final int unitsPerDay;

    public DailyAmountPace(long dailyScheduleId, @NonNull LocalDate startDate, int unitDepth, int unitsPerDay) {
        if (unitDepth < 1)
            throw new IllegalArgumentException("unitDepth must be >= 1. got: " + unitDepth);
        if (unitsPerDay < 1)
            throw new IllegalArgumentException("unitsPerDay must be >= 1. got: " + unitsPerDay);
        this.dailyScheduleId = dailyScheduleId;
        this.startDate = startDate;
        this.unitDepth = unitDepth;
        this.unitsPerDay = unitsPerDay;
    }

    @Override
    public long getDailyScheduleId() {
        return dailyScheduleId;
    }

    @NonNull
    public LocalDate getStartDate() {
        return startDate;
    }

    public int getUnitDepth() {
        return unitDepth;
    }

    public int getUnitsPerDay() {
        return unitsPerDay;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DailyAmountPace that = (DailyAmountPace) o;
        return dailyScheduleId == that.dailyScheduleId && unitDepth == that.unitDepth && unitsPerDay == that.unitsPerDay && Objects.equals(startDate, that.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dailyScheduleId, startDate, unitDepth, unitsPerDay);
    }

    @NonNull
    @Override
    public String toString() {
        return "DailyAmountPace{" +
                "dailyScheduleId=" + dailyScheduleId +
                ", startDate=" + startDate +
                ", unitDepth=" + unitDepth +
                ", unitsPerDay=" + unitsPerDay +
                '}';
    }
}