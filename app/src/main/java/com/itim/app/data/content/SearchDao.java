package com.itim.app.data.content;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Dao;
import androidx.room.Query;

import com.google.common.util.concurrent.ListenableFuture;
import com.itim.app.model.content.Component;
import com.itim.app.model.content.Source;

import java.util.List;

@Dao
public interface SearchDao {

    @Query("SELECT * "
            + "FROM sources "
            + "WHERE id IN (SELECT sourceId FROM source_downloads WHERE installedVersion IS NOT NULL) "
            + "AND EXISTS ("
            + "SELECT 1"
            + "FROM search_index "
            + "WHERE rowid = ("
            + "SELECT c.id "
            + "FROM components AS c "
            + "WHERE c.sourceId = sources.id "
            + "AND c.text IS NOT NULL "
            + "AND c.orderIndex >= 1 "
            + "ORDER BY c.orderIndex "
            + "LIMIT 1"
            + ")"
            + ")"
            + "ORDER BY id")
    @NonNull
    ListenableFuture<List<Source>> getOfflineSearchableSources();

    @Query("SELECT * "
            + "FROM components "
            + "WHERE id IN (SELECT rowid FROM search_index WHERE search_index MATCH :matchQuery) "
            + "AND (:sourceId IS NULL OR sourceId = :sourceId) "
            + "ORDER BY sourceId, orderIndex "
            + "LIMIT :pageSize "
            + "OFFSET :pageOffset")
    @NonNull
    ListenableFuture<List<Component>> getTextSearchPage(String matchQuery, @Nullable Long sourceId, int pageSize, int pageOffset);

}
