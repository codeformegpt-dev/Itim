package com.itim.app.model.state;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Objects;

@Entity(
        tableName = "end_date_pace",
        foreignKeys = @ForeignKey(
                entity = DailySchedule.class,
                parentColumns = "id",
                childColumns = "dailyScheduleId"
        )
)
public final class EndDatePace implements StudyPace {

    @PrimaryKey
    private final long dailyScheduleId;
    private final LocalDate startDate;
    private final LocalDate targetEndDate;

    public EndDatePace(long dailyScheduleId, LocalDate startDate, LocalDate targetEndDate) {
        this.dailyScheduleId = dailyScheduleId;
        this.startDate = startDate;
        this.targetEndDate = targetEndDate;
    }

    @Override
    public long getDailyScheduleId() {
        return dailyScheduleId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getTargetEndDate() {
        return targetEndDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EndDatePace that = (EndDatePace) o;
        return dailyScheduleId == that.dailyScheduleId && Objects.equals(startDate, that.startDate) && Objects.equals(targetEndDate, that.targetEndDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dailyScheduleId, startDate, targetEndDate);
    }

    @NonNull
    @Override
    public String toString() {
        return "EndDatePace{" +
                "dailyScheduleId=" + dailyScheduleId +
                ", startDate=" + startDate +
                ", targetEndDate=" + targetEndDate +
                '}';
    }
}
