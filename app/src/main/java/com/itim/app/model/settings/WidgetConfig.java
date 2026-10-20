package com.itim.app.model.settings;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.itim.app.model.content.Source;

import java.util.Objects;

@Entity(
        tableName = "widgetConfig",
        foreignKeys = @ForeignKey(
                entity = Source.class,
                parentColumns = "id",
                childColumns = "sourceId",
                onDelete = ForeignKey.CASCADE
        )
)
public final class WidgetConfig {

    @PrimaryKey
    private final int id;
    private final long sourceId;

    public WidgetConfig(int id, long sourceId) {
        this.id = id;
        this.sourceId = sourceId;
    }

    public int getId() {
        return id;
    }

    public long getSourceId() {
        return sourceId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        WidgetConfig that = (WidgetConfig) o;
        return id == that.id && sourceId == that.sourceId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sourceId);
    }

    @NonNull
    @Override
    public String toString() {
        return "WidgetConfig{" +
                "id=" + id +
                ", sourceId=" + sourceId +
                '}';
    }
}
