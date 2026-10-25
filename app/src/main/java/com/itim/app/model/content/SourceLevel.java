package com.itim.app.model.content;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.util.Objects;

@Entity(
        tableName = "source_level",
        primaryKeys = {"sourceId", "depth"},
        foreignKeys = @ForeignKey(
                entity = Source.class,
                parentColumns = "id",
                childColumns = "sourceId",
                onDelete = ForeignKey.CASCADE
        )
)
public final class SourceLevel {

    private final long sourceId;
    private final int depth;
    @NonNull
    private final String name;

    public SourceLevel(long sourceId, int depth, @NonNull String name) {
        if(depth < 1)
            throw new IllegalArgumentException("depth must be >= 1. got: " + depth);
        this.sourceId = sourceId;
        this.depth = depth;
        this.name = name;
    }

    public long getSourceId() {
        return sourceId;
    }

    public int getDepth() {
        return depth;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SourceLevel that = (SourceLevel) o;
        return sourceId == that.sourceId && depth == that.depth && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceId, depth, name);
    }

    @Override
    public String toString() {
        return "SourceLevel{" +
                "sourceId=" + sourceId +
                ", depth=" + depth +
                ", name='" + name + '\'' +
                '}';
    }
}
