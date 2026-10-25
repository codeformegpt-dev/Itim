package com.itim.app.model.content;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(
        tableName = "source_schedules",
        foreignKeys = @ForeignKey(
                entity = Source.class,
                parentColumns = "id",
                childColumns = "sourceId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = @Index(value = {"sourceId", "id"}, unique = true)
)
public final class SourceSchedule {

    @PrimaryKey
    private final long id;
    private final long sourceId;
    @NonNull
    private final String title;

    public SourceSchedule(long id, long sourceId, @NonNull String title) {
        this.id = id;
        this.sourceId = sourceId;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public long getSourceId() {
        return sourceId;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SourceSchedule that = (SourceSchedule) o;
        return id == that.id && sourceId == that.sourceId && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sourceId, title);
    }

    @NonNull
    @Override
    public String toString() {
        return "SourceSchedule{" +
                "id=" + id +
                ", sourceId=" + sourceId +
                ", title='" + title + '\'' +
                '}';
    }
}
