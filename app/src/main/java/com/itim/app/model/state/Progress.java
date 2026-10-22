package com.itim.app.model.state;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(
        tableName = "progress",
        indices = {
                @Index(value = {"mode", "sourceId"}, unique = true),
                @Index("componentId")
        }
)
public final class Progress {
    @PrimaryKey(autoGenerate = true)
    private final long id;
    @NonNull
    private final Mode mode;
    private final long sourceId;
    private final long componentId;

    public Progress(long id, @NonNull Mode mode, long sourceId, long componentId) {
        this.id = id;
        this.mode = mode;
        this.sourceId = sourceId;
        this.componentId = componentId;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public Mode getMode() {
        return mode;
    }

    public long getSourceId() {
        return sourceId;
    }

    public long getComponentId() {
        return componentId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Progress progress = (Progress) o;
        return id == progress.id && sourceId == progress.sourceId && componentId == progress.componentId && mode == progress.mode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mode, sourceId, componentId);
    }

    @NonNull
    @Override
    public String toString() {
        return "Progress{" +
                "id=" + id +
                ", mode=" + mode +
                ", sourceId=" + sourceId +
                ", componentId=" + componentId +
                '}';
    }
}
