package com.itim.app.data.content;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Upsert;

import com.google.common.util.concurrent.ListenableFuture;
import com.itim.app.model.content.Source;
import com.itim.app.model.distribution.SourceDownload;

import java.util.List;

@Dao
public abstract class SourceDao {


    @Query("SELECT * "
            + "FROM sources "
            + "WHERE id IN (SELECT sourceId FROM source_downloads WHERE installedVersion IS NOT NULL) "
            + "ORDER BY id")
    @NonNull
    public abstract LiveData<List<Source>> observeInstalledSources();

    @Query("SELECT s.id, s.label, d.sourceId, d.installedVersion, d.targetVersion, d.completedParts, d.totalParts, d.lastCheckedAt "
            + "FROM sources AS s "
            + "LEFT JOIN source_downloads AS d ON d.sourceId = s.id "
            + "ORDER BY s.id")
    @NonNull
    public abstract LiveData<List<SourceWithDownload>> observeSourcesWithDownload();

    @Query("SELECT s.id, s.label, d.sourceId, d.installedVersion, d.targetVersion, d.completedParts, d.totalParts, d.lastCheckedAt "
            + "FROM sources AS s "
            + "LEFT JOIN source_downloads AS d ON d.sourceId = s.id "
            + "ORDER BY s.id")
    @NonNull
    public abstract ListenableFuture<List<SourceWithDownload>> getSourcesWithDownload();

    @Query("SELECT * "
            + "FROM source_downloads "
            + "WHERE sourceId = :sourceId")
    @NonNull
    public abstract ListenableFuture<SourceDownload> getSourceDownload(long sourceId);

    @Query("SELECT * "
            + "FROM source_downloads "
            + "WHERE sourceId = :sourceId")
    @NonNull
    public abstract LiveData<SourceDownload> observeSourceDownload(long sourceId);

    @Query("SELECT sourceId "
            + "FROM source_downloads "
            + "WHERE installedVersion IS NOT NULL "
            + "ORDER BY sourceId")
    @NonNull
    public abstract List<Long> getInstalledSourceIds();

    @Query("UPDATE source_downloads "
            + "SET completedParts = :completedParts "
            + "WHERE sourceId = :sourceId")
    @NonNull
    public abstract ListenableFuture<Void> updateCompletedParts(long sourceId, int completedParts);

    @Query("DELETE FROM sources "
            + "WHERE id = :sourceId "
            + "AND NOT EXISTS (SELECT 1 FROM source_downloads WHERE sourceId = :sourceId AND installedVersion IS NOT NULL)")
    @NonNull
    public abstract ListenableFuture<Void> deleteSourceIfNotInstalled(long sourceId);

    @Upsert
    abstract void upsertSource(@NonNull Source source);

    @Upsert
    abstract void upsertSourceDownload(@NonNull SourceDownload download);

    @Transaction
    public void startFirstDownload(long sourceId,  @NonNull String label, int targetVersion, int totalParts){
        upsertSource(new Source(sourceId, label));
        upsertSourceDownload(new SourceDownload(sourceId, null, targetVersion, totalParts, 0, null));
    }



}
