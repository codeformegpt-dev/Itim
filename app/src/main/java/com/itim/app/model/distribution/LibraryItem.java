package com.itim.app.model.distribution;

import java.util.Objects;

public final class LibraryItem {

    public enum LibraryState {
        AVAILABLE,
        DOWNLOADING,
        PAUSED,
        INSTALLED
    }

    private final long sourceId;
    private final String label;
    private final LibraryState state;
    private final Integer progressPercent;

    private LibraryItem(long sourceId, String label, LibraryState state, Integer progressPercent) {
        this.sourceId = sourceId;
        this.label = label;
        this.state = state;
        this.progressPercent = progressPercent;

        boolean inProgress = state == LibraryState.DOWNLOADING || state == LibraryState.PAUSED;
        if (inProgress) {
            if (progressPercent == null || progressPercent < 0 || progressPercent > 100)
                throw new IllegalArgumentException("progressPercent must be 0-100 in " + state + ", got " + progressPercent);
        } else if (progressPercent != null)
            throw new IllegalArgumentException("progressPercent must be null in " + state);

    }

    public static LibraryItem of(long sourceId, String label, SourceDownload download, boolean downloadRunning) {
        if (download == null || (download.getInstalledVersion() == null && download.getTargetVersion() == null))
            return new LibraryItem(sourceId, label, LibraryState.AVAILABLE, null);

        if (download.getInstalledVersion() != null)
            return new LibraryItem(sourceId, label, LibraryState.INSTALLED, null);
        
        int percent = download.getCompletedParts() * 100 / download.getTotalParts();
        LibraryState state = downloadRunning ? LibraryState.DOWNLOADING : LibraryState.PAUSED;
        return new LibraryItem(sourceId, label, state, percent);
    }

    public long getSourceId() {
        return sourceId;
    }

    public String getLabel() {
        return label;
    }

    public LibraryState getState() {
        return state;
    }

    public Integer getProgressPercent() {
        return progressPercent;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LibraryItem that = (LibraryItem) o;
        return sourceId == that.sourceId && Objects.equals(label, that.label) && state == that.state && Objects.equals(progressPercent, that.progressPercent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceId, label, state, progressPercent);
    }
}
