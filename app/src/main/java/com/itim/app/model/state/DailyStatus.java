package com.itim.app.model.state;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public final class DailyStatus {

    public enum DailyState{
        NO_PORTION,
        NOT_STARTED,
        IN_PROGRESS,
        COMPLETED
    }

    private final long sourceId;
    @NonNull
    private final String label;
    @Nullable
    private final String portionTitle;
    @NonNull
    private final DailyState state;

    public DailyStatus(long sourceId, @NonNull String label, @Nullable String portionTitle, DailyState state) {
        this.sourceId = sourceId;
        this.label = label;
        this.portionTitle = portionTitle;
        this.state = state;
    }

    public long getSourceId() {
        return sourceId;
    }

    @NonNull
    public String getLabel() {
        return label;
    }

    @Nullable
    public String getPortionTitle() {
        return portionTitle;
    }

    public DailyState getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DailyStatus that = (DailyStatus) o;
        return sourceId == that.sourceId && Objects.equals(label, that.label) && Objects.equals(portionTitle, that.portionTitle) && state == that.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceId, label, portionTitle, state);
    }

    @NonNull
    @Override
    public String toString() {
        return "DailyStatus{" +
                "sourceId=" + sourceId +
                ", label='" + label + '\'' +
                ", portionTitle='" + portionTitle + '\'' +
                ", state=" + state +
                '}';
    }
}
