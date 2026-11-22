package com.itim.app.data.content;

import androidx.annotation.NonNull;
import androidx.room.Embedded;

import com.itim.app.model.content.Commentary;

import java.util.Objects;

public final class CommentaryWithCommentator {

   @Embedded
    @NonNull
    private final Commentary commentary;
   @NonNull
    private final String commentatorName;

    public CommentaryWithCommentator(@NonNull Commentary commentary, @NonNull String commentatorName) {
        this.commentary = commentary;
        this.commentatorName = commentatorName;
    }

    @NonNull
    public Commentary getCommentary() {
        return commentary;
    }

    @NonNull
    public String getCommentatorName() {
        return commentatorName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CommentaryWithCommentator that = (CommentaryWithCommentator) o;
        return Objects.equals(commentary, that.commentary) && Objects.equals(commentatorName, that.commentatorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentary, commentatorName);
    }
}
