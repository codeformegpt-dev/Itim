package com.itim.app.data.remote;


import java.util.Objects;

public class SourceRelease {

    private final long sourceId;
    private final int version;
    private final int totalParts;

    public SourceRelease(long sourceId, int version, int totalParts) {
        this.sourceId = sourceId;
        this.version = version;
        this.totalParts = totalParts;
    }

    public long getSourceId() {
        return sourceId;
    }

    public int getVersion() {
        return version;
    }

    public int getTotalParts() {
        return totalParts;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SourceRelease that = (SourceRelease) o;
        return sourceId == that.sourceId && version == that.version && totalParts == that.totalParts;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceId, version, totalParts);
    }
}