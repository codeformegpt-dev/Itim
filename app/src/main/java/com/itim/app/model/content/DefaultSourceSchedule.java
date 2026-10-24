package com.itim.app.model.content;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(
        tableName = "default_source_schedule",
        foreignKeys = {
                @ForeignKey(
                    entity = Source.class,
                    parentColumns = "id",
                    childColumns = "sourceId",
                    onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = SourceSchedule.class,
                        parentColumns = {"id", "sourceId"},
                        childColumns = {"sourceId", "sourceScheduleId"}
                )
        },
        indices = @Index(value = {"sourceId", "sourceScheduleId"}, unique = true)

)
public final class DefaultSourceSchedule {
    @PrimaryKey
    private final long sourceId;
    private final long sourceScheduleId;

    public DefaultSourceSchedule(long sourceId, long sourceScheduleId) {
        this.sourceId = sourceId;
        this.sourceScheduleId = sourceScheduleId;
    }

    public long getSourceId() {
        return sourceId;
    }

    public long getSourceScheduleId() {
        return sourceScheduleId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DefaultSourceSchedule that = (DefaultSourceSchedule) o;
        return sourceId == that.sourceId && sourceScheduleId == that.sourceScheduleId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceId, sourceScheduleId);
    }

    @NonNull
    @Override
    public String toString() {
        return "DefaultSourceSchedule{" +
                "sourceId=" + sourceId +
                ", sourceScheduleId=" + sourceScheduleId +
                '}';
    }


}
