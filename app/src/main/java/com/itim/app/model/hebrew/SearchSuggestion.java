package com.itim.app.model.hebrew;

import androidx.annotation.NonNull;

import java.util.List;

public final class SearchSuggestion {

    private final long componentId;
    private final long sourceId;
    private final boolean installed;
    @NonNull
    private final List<String> path;

    public SearchSuggestion(long componentId, long sourceId, boolean installed, @NonNull List<String> path) {
        this.componentId = componentId;
        this.sourceId = sourceId;
        this.installed = installed;
        this.path = List.copyOf(path);
    }

    public long getComponentId() {
        return componentId;
    }

    public long getSourceId() {
        return sourceId;
    }

    public boolean isInstalled() {
        return installed;
    }

    @NonNull
    public List<String> getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SearchSuggestion that = (SearchSuggestion) o;
        return componentId == that.componentId;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(componentId);
    }

    @NonNull
    @Override
    public String toString() {
        return "SearchSuggestion{" +
                "componentId=" + componentId +
                ", sourceId=" + sourceId +
                ", installed=" + installed +
                ", path=" + path +
                '}';
    }


}
