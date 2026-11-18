package com.itim.app.data.content;


import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Query;

import com.google.common.util.concurrent.ListenableFuture;
import com.itim.app.model.content.Component;

import java.util.List;

@Dao
public interface ComponentDao {

    @Query("SELECT * "
            + "FROM components "
            + "WHERE sourceId = :sourceId "
            + "AND orderIndex BETWEEN :fromOrderIndex AND :toOrderIndex "
            + "ORDER BY orderIndex")
    @NonNull
    ListenableFuture<List<Component>> getComponentRange(long sourceId, int fromOrderIndex, int toOrderIndex);

    @Query("SELECT * "
            + "FROM components "
            + "WHERE sourceId = :sourceId "
            + "AND text IS NULL "
            + "ORDER BY orderIndex")
    @NonNull
    ListenableFuture<List<Component>> getNonLeafComponents(long sourceId);

    @Query("SELECT * "
            + "FROM components "
            + "WHERE id = :componentId")
    @NonNull
    ListenableFuture<Component> getComponent(long componentId);

    @Query("SELECT * "
            + "FROM components "
            + "WHERE sourceId = :sourceId "
            + "AND text IS NOT NULL "
            + "AND orderIndex >= :fromOrderIndex "
            + "ORDER BY orderIndex "
            + "LIMIT 1")
    @NonNull
    ListenableFuture<Component> getFirstFrom(long sourceId, int fromOrderIndex);

}
