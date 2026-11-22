package com.itim.app.data.content;

import androidx.annotation.NonNull;
import androidx.room.Embedded;

import com.itim.app.model.content.SchedulePortion;

import java.util.Objects;

public final class PortionWithBounds {

    @Embedded
    @NonNull
    private final SchedulePortion portion;
    private final int startOrderIndex;
    @NonNull
    private final String startLabel;
    private final int endOrderIndex;
    @NonNull
    private final String endLabel;

    public PortionWithBounds(@NonNull SchedulePortion portion, int startOrderIndex, @NonNull String startLabel, int endOrderIndex, @NonNull String endLabel) {
        this.portion = portion;
        this.startOrderIndex = startOrderIndex;
        this.startLabel = startLabel;
        this.endOrderIndex = endOrderIndex;
        this.endLabel = endLabel;
    }

    @NonNull
    public SchedulePortion getPortion() {
        return portion;
    }

    public int getStartOrderIndex() {
        return startOrderIndex;
    }

    @NonNull
    public String getStartLabel() {
        return startLabel;
    }

    public int getEndOrderIndex() {
        return endOrderIndex;
    }

    @NonNull
    public String getEndLabel() {
        return endLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PortionWithBounds that = (PortionWithBounds) o;
        return startOrderIndex == that.startOrderIndex && endOrderIndex == that.endOrderIndex && Objects.equals(portion, that.portion) && Objects.equals(startLabel, that.startLabel) && Objects.equals(endLabel, that.endLabel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(portion, startOrderIndex, startLabel, endOrderIndex, endLabel);
    }
}
