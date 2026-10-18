package com.itim.app.model.hebrew;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class SearchResult {

    private final long componentId;
    private final long sourceId;
    private final boolean installed;
    @NonNull
    private final List<String> path;
    @NonNull
    private final String snippet;

    public SearchResult(long componentId, long sourceId, boolean installed, @NonNull List<String> path, @NonNull String snippet) {
        this.componentId = componentId;
        this.sourceId = sourceId;
        this.installed = installed;
        this.path = List.copyOf(path);
        this.snippet = Objects.requireNonNull(snippet);
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

    @NonNull
    public String getSnippet() {
        return snippet;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SearchResult that = (SearchResult) o;
        return componentId == that.componentId;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(componentId);
    }

    @NonNull
    @Override
    public String toString() {
        return "SearchResult{" +
                "componentId=" + componentId +
                ", sourceId=" + sourceId +
                ", installed=" + installed +
                ", path=" + path +
                ", snippet='" + snippet + '\'' +
                '}';
    }
}
