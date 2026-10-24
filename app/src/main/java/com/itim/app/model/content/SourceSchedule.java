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
        tableName = "source_schedule",
        foreignKeys = {
                        @ForeignKey(
                                    entity = Source.class,
                                    parentColumns = "id",
                                    childColumns = "sourceId",
                                    onDelete = ForeignKey.CASCADE
                        ),
                        @ForeignKey(
                                    entity = Component.class,
                                    parentColumns = "id",
                                    childColumns = "startComponentId",
                                    onDelete = ForeignKey.RESTRICT
                        ),
                        @ForeignKey(
                                    entity = Component.class,
                                    parentColumns = "id",
                                    childColumns = "endComponentId",
                                    onDelete = ForeignKey.RESTRICT
                )
        },
        indices = {
                @Index(value = {"id", "sourceId"}, unique = true),
                @Index(value = {"sourceId", "date"}, unique = true),
                @Index("title"),
                @Index("startComponentId"),
                @Index("endComponentId")
        }
)
public final class SourceSchedule {

    @PrimaryKey
    private final long id;
    private final long sourceId;
    @NonNull
    private final LocalDate date;
    private final long startComponentId;
    private final long endComponentId;
    @Nullable
    private final String title;

    public SourceSchedule(long id, long sourceId, @NonNull LocalDate date, long startComponentId, long endComponentId, @Nullable String title) {
        this.id = id;
        this.sourceId = sourceId;
        this.date = date;
        this.startComponentId = startComponentId;
        this.endComponentId = endComponentId;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public long getSourceId() {
        return sourceId;
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
        SourceSchedule that = (SourceSchedule) o;
        return id == that.id && sourceId == that.sourceId && startComponentId == that.startComponentId && endComponentId == that.endComponentId && Objects.equals(date, that.date) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sourceId, date, startComponentId, endComponentId, title);
    }

    @NonNull
    @Override
    public String toString() {
        return "SourceSchedule{" +
                "id=" + id +
                ", sourceId=" + sourceId +
                ", date=" + date +
                ", startComponentId=" + startComponentId +
                ", endComponentId=" + endComponentId +
                ", title='" + title + '\'' +
                '}';
    }
}
