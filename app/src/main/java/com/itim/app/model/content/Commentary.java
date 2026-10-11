package com.itim.app.model.content;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(
        tableName = "commentary",
        foreignKeys = {
                        @ForeignKey(
                                entity = Component.class,
                                parentColumns = "id",
                                childColumns = "componentId",
                                onDelete = ForeignKey.CASCADE
                        ),
                        @ForeignKey(
                                entity = Commentator.class,
                                parentColumns = "id",
                                childColumns = "commentatorId",
                                onDelete = ForeignKey.CASCADE
                        )
        },
        indices = {
                    @Index(value = {"componentId", "commentatorId", "orderIndex"}, unique = true),
                    @Index("commentatorId"),
                    @Index("title")
        }
)
public class Commentary {

    @PrimaryKey
    private final long id;
    private final long componentId;
    private final long commentatorId;
    @Nullable
    private final String title;
    private final int orderIndex;
    @NonNull
    private final String text;

    public Commentary(long id, long componentId, long commentatorId, @Nullable String title, int orderIndex, @NonNull String text) {
        this.id = id;
        this.componentId = componentId;
        this.commentatorId = commentatorId;
        this.title = title;
        this.orderIndex = orderIndex;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public long getComponentId() {
        return componentId;
    }

    public long getCommentatorId() {
        return commentatorId;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    @NonNull
    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Commentary that = (Commentary) o;
        return id == that.id && componentId == that.componentId && commentatorId == that.commentatorId && orderIndex == that.orderIndex && Objects.equals(title, that.title) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, componentId, commentatorId, title, orderIndex, text);
    }
}
