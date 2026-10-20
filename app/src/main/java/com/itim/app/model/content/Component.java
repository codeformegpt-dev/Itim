package com.itim.app.model.content;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(
        tableName   = "component",
        foreignKeys = {@ForeignKey(
                        entity = Source.class,
                        parentColumns = "id",
                        childColumns = "sourceId",
                        onDelete = ForeignKey.CASCADE
        ),
                        @ForeignKey(
                                entity = Component.class,
                                parentColumns = "id",
                                childColumns = "parentId",
                                onDelete = ForeignKey.CASCADE

                        )},
        indices   = {@Index(value = {"sourceId", "orderIndex"}, unique = true),
                     @Index("parentId")
        }
)
public final class Component {

    @PrimaryKey
    private final long id;
    private final long sourceId;
    @Nullable
    private final Long parentId;
    private final int orderIndex;
    @NonNull
    private final String label;
    @Nullable
    private final String text;

    public Component(long id, long sourceId, @Nullable Long parentId, int orderIndex, @NonNull String label, @Nullable String text){
        this.id = id;
        this.sourceId = sourceId;
        this.parentId = parentId;
        this.orderIndex = orderIndex;
        this.label = label;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public long getSourceId() {
        return sourceId;
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

    @Nullable
    public String getText() {
        return text;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Component component = (Component) o;
        return id == component.id && sourceId == component.sourceId && orderIndex == component.orderIndex && Objects.equals(parentId, component.parentId) && Objects.equals(label, component.label) && Objects.equals(text, component.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sourceId, parentId, orderIndex, label, text);
    }

    @NonNull
    @Override
    public String toString() {
        return "Component{" +
                "id=" + id +
                ", sourceId=" + sourceId +
                ", parentId=" + parentId +
                ", orderIndex=" + orderIndex +
                ", label='" + label + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
