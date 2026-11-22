package com.itim.app.data.content;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public final class ComponentOutline {

    private final long id;
    @Nullable
    private final Long parentId;
    private final int orderIndex;
    @NonNull
    private final String label;
    private final boolean isLeaf;

    public ComponentOutline(long id, @Nullable Long parentId, int orderIndex, @NonNull String label, boolean isLeaf) {
        this.id = id;
        this.parentId = parentId;
        this.orderIndex = orderIndex;
        this.label = label;
        this.isLeaf = isLeaf;
    }

    public long getId() {
        return id;
    }

    @Nullable
    public Long getParentId() {
        return parentId;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    @NonNull
    public String getLabel() {
        return label;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ComponentOutline that = (ComponentOutline) o;
        return id == that.id && orderIndex == that.orderIndex && isLeaf == that.isLeaf && Objects.equals(parentId, that.parentId) && Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parentId, orderIndex, label, isLeaf);
    }
}
