package com.itim.app.model.state;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Objects;

@Entity(
        tableName = "daily_schedule",
        indices = @Index("sourceId")
)
public final class DailySchedule {
    @PrimaryKey(autoGenerate = true)
    private final long id;
    private final long sourceId;
    private final int offsetDays;
    @Nullable
    private final LocalDate lastCompletedDate;

    public DailySchedule(long id, long sourceId, int offsetDays, @Nullable LocalDate lastCompletedDate) {
        this.id = id;
        this.sourceId = sourceId;
        this.offsetDays = offsetDays;
        this.lastCompletedDate = lastCompletedDate;
    }

    public long getId() {
        return id;
    }

    public long getSourceId() {
        return sourceId;
    }

    public int getOffsetDays() {
        return offsetDays;
    }

    @Nullable
    public LocalDate getLastCompletedDate() {
        return lastCompletedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DailySchedule that = (DailySchedule) o;
        return id == that.id && sourceId == that.sourceId && offsetDays == that.offsetDays && Objects.equals(lastCompletedDate, that.lastCompletedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sourceId, offsetDays, lastCompletedDate);
    }

    @NonNull
    @Override
    public String toString() {
        return "DailySchedule{" +
                "id=" + id +
                ", sourceId=" + sourceId +
                ", offsetDays=" + offsetDays +
                ", lastCompletedDate=" + lastCompletedDate +
                '}';
    }


}
