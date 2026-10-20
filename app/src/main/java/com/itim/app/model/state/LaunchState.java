package com.itim.app.model.state;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.itim.app.model.content.Source;

import java.util.Objects;

@Entity(
        tableName = "launchState",
        foreignKeys = @ForeignKey(
                entity = Source.class,
                parentColumns = "id",
                childColumns = "sourceId"
        ),
        indices = @Index("sourceId")

)
public final class LaunchState {

    @PrimaryKey
    private final long id;
    @Nullable
    private final Mode mode;
    @Nullable
    private final Long sourceId;

    public LaunchState(long id, @Nullable Mode mode, @Nullable Long sourceId) {
        this.id = id;
        this.mode = mode;
        this.sourceId = sourceId;
    }

    public long getId() {
        return id;
    }

    @Nullable
    public Mode getMode() {
        return mode;
    }

    @Nullable
    public Long getSourceId() {
        return sourceId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LaunchState that = (LaunchState) o;
        return id == that.id && mode == that.mode && Objects.equals(sourceId, that.sourceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mode, sourceId);
    }

    @NonNull
    @Override
    public String toString() {
        return "LaunchState{" +
                "id=" + id +
                ", mode=" + mode +
                ", sourceId=" + sourceId +
                '}';
    }
}
