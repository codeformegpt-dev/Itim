package com.itim.app.data.content;


import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Query;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

@Dao
public interface CommentaryDao {

    @Query("SELECT cy.id, cy.componentId, cy.commentatorId, cy.title, cy.orderIndex, cy.text, ct.name AS commentatorName "
            + "FROM commentaries AS cy "
            + "INNER JOIN commentators AS ct ON ct.id = cy.id "
            + "WHERE cy.componentId = :componentId "
            + "ORDER BY cy.orderIndex")
    @NonNull
    ListenableFuture<List<CommentaryWithCommentator>> getCommentaries(long componentId);

    @Query("SELECT EXISTS (SELECT 1 FROM commentators WHERE sourceId = :sourceId AND isBasic = 0)")
    @NonNull
    ListenableFuture<Boolean> getHasAdditionalCommentators(long sourceId);

}
