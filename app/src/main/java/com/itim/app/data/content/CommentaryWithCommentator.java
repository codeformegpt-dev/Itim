package com.itim.app.data.content;

import androidx.annotation.NonNull;

import java.util.Objects;

public final class CommentaryWithCommentator {

    private final long id;
    private final long componentId;
    private final long commentatorId;
    private final String title;
    private final int orderIndex;
    @NonNull
    private final String text;
    private final String commentatorName;

    public CommentaryWithCommentator(long id, long componentId, long commentatorId, String title, int orderIndex, @NonNull String text, String commentatorName) {
        this.id = id;
        this.componentId = componentId;
        this.commentatorId = commentatorId;
        this.title = title;
        this.orderIndex = orderIndex;
        this.text = text;
        this.commentatorName = commentatorName;
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

    public String getCommentatorName() {
        return commentatorName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CommentaryWithCommentator that = (CommentaryWithCommentator) o;
        return id == that.id && componentId == that.componentId && commentatorId == that.commentatorId && orderIndex == that.orderIndex && Objects.equals(title, that.title) && Objects.equals(text, that.text) && Objects.equals(commentatorName, that.commentatorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, componentId, commentatorId, title, orderIndex, text, commentatorName);
    }

    @NonNull
    @Override
    public String toString() {
        return "CommentaryWithCommentator{" +
                "id=" + id +
                ", componentId=" + componentId +
                ", commentatorId=" + commentatorId +
                ", title='" + title + '\'' +
                ", orderIndex=" + orderIndex +
                ", text='" + text + '\'' +
                ", commentatorName='" + commentatorName + '\'' +
                '}';
    }
}
