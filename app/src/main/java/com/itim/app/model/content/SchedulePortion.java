package com.itim.app.model.content;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Objects;

@Entity(
        tableName = "schedule_portion",
        foreignKeys = {
                        @ForeignKey(
                                    entity = SourceSchedule.class,
                                    parentColumns = "id",
                                    childColumns = "scheduleId",
                                    onDelete = ForeignKey.CASCADE
                        ),
                        @ForeignKey(
                                    entity = Component.class,
                                    parentColumns = "id",
                                    childColumns = "startComponentId",
                                    onDelete = ForeignKey.NO_ACTION
                        ),
                        @ForeignKey(
                                    entity = Component.class,
                                    parentColumns = "id",
                                    childColumns = "endComponentId",
                                    onDelete = ForeignKey.NO_ACTION
                )
        },
        indices = {
                @Index(value = {"scheduleId", "date"}, unique = true),
                @Index("title"),
                @Index("startComponentId"),
                @Index("endComponentId")
        }
)
public final class SchedulePortion {

    @PrimaryKey
    private final long id;
    private final long scheduleId;
    @NonNull
    private final LocalDate date;
    private final long startComponentId;
    private final long endComponentId;
    @Nullable
    private final String title;

    public SchedulePortion(long id, long scheduleId, @NonNull LocalDate date, long startComponentId, long endComponentId, @Nullable String title) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.date = date;
        this.startComponentId = startComponentId;
        this.endComponentId = endComponentId;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public long getScheduleId() {
        return scheduleId;
    }

    @NonNull
    public LocalDate getDate() {
        return date;
    }

    public long getStartComponentId() {
        return startComponentId;
    }

    public long getEndComponentId() {
        return endComponentId;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SchedulePortion that = (SchedulePortion) o;
        return id == that.id && scheduleId == that.scheduleId && startComponentId == that.startComponentId && endComponentId == that.endComponentId && Objects.equals(date, that.date) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, scheduleId, date, startComponentId, endComponentId, title);
    }

    @NonNull
    @Override
    public String toString() {
        return "SourceSchedule{" +
                "id=" + id +
                ", scheduleId=" + scheduleId +
                ", date=" + date +
                ", startComponentId=" + startComponentId +
                ", endComponentId=" + endComponentId +
                ", title='" + title + '\'' +
                '}';
    }
}
