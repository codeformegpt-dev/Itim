package com.itim.app.data.personal;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.google.common.util.concurrent.ListenableFuture;
import com.itim.app.model.state.Mode;
import com.itim.app.model.state.Progress;

import java.util.List;

@Dao
public abstract class ProgressDao {

    @Query("SELECT * "
            + "FROM progress "
            + "WHERE mode = :mode AND sourceId = :sourceId")
    public abstract ListenableFuture<Progress> getProgress(Mode mode, long sourceId);

    @Query("SELECT * "
            + "FROM progress "
            + "WHERE mode = :mode AND sourceId = :sourceId")
    public abstract LiveData<Progress> observeProgress(Mode mode, long sourceId);

    @Query("DELETE FROM progress "
            + "WHERE mode = :mode AND sourceId = :sourceId")
    public abstract ListenableFuture<Void> deleteProgress(Mode mode, long sourceId);

    @Query("SELECT * "
            + "FROM progress "
            + "WHERE sourceId = :sourceId")
    public abstract ListenableFuture<List<Progress>> getProgressOfSource(long sourceId);


    @Transaction
    public void upsertProgress(Mode mode, long sourceId, long componentId) {
        int updated = updateProgressComponent(mode, sourceId, componentId);
        if (updated == 0) insertProgress(new Progress(0, mode, sourceId, componentId));
    }

    @Query("UPDATE progress "
            + "SET componentId = :componentId "
            + "WHERE mode = :mode AND sourceId = :sourceId")
    abstract int updateProgressComponent(Mode mode, long sourceId, long componentId);

    @Insert
    abstract void insertProgress(Progress progress);


























}
