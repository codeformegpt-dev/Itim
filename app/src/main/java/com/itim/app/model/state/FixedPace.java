package com.itim.app.model.state;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(
        tableName = "fixed_pace",
        foreignKeys = @ForeignKey(
                entity = DailySchedule.class,
                parentColumns = "id",
                childColumns = "dailyScheduleId",
                onDelete = ForeignKey.CASCADE
        )
)
public final class FixedPace implements StudyPace {

    @PrimaryKey
    private final long dailyScheduleId;
    private final long sourceScheduleId;

    public FixedPace(long sourceScheduleId, long dailyScheduleId) {
        this.sourceScheduleId = sourceScheduleId;
        this.dailyScheduleId = dailyScheduleId;
    }

    @Override
    public long getDailyScheduleId() {
        return dailyScheduleId;
    }

    public long getSourceScheduleId() {
        return sourceScheduleId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FixedPace fixedPace = (FixedPace) o;
        return dailyScheduleId == fixedPace.dailyScheduleId && sourceScheduleId == fixedPace.sourceScheduleId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dailyScheduleId, sourceScheduleId);
    }

    @NonNull
    @Override
    public String toString() {
        return "FixedPace{" +
                "dailyScheduleId=" + dailyScheduleId +
                ", sourceScheduleId=" + sourceScheduleId +
                '}';
    }
}
