package com.itim.app.data.content;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.google.common.util.concurrent.ListenableFuture;
import com.itim.app.model.content.Commentary;
import com.itim.app.model.content.Commentator;
import com.itim.app.model.content.Component;
import com.itim.app.model.content.DefaultSourceSchedule;
import com.itim.app.model.content.SchedulePortion;
import com.itim.app.model.content.SourceLevel;
import com.itim.app.model.content.SourceSchedule;
import com.itim.app.model.hebrew.SearchIndex;

import java.util.List;

@Dao
public  abstract class InstallDao {

    @Insert
    public abstract void insertCommentators(List<Commentator> rows);
    @Insert
    public abstract void insertComponents(List<Component> rows);
    @Insert
    public abstract void insertCommentaries(List<Commentary> rows);
    @Insert
    public abstract void insertLevels(List<SourceLevel> rows);
    @Insert
    public abstract void insertSchedules(List<SourceSchedule> rows);
    @Insert
    public abstract void insertPortions(List<SchedulePortion> rows);
    @Insert
    public abstract void insertDefaultSchedule(List<DefaultSourceSchedule> rows);
    @Insert
    public abstract void insertSearchIndex(List<SearchIndex> rows);

    @Query("UPDATE source_downloads "
            + "SET installedVersion  = targetVersion, targetVersion = NULL, totalParts = NULL,  completedParts = NUlL "
            + "WHERE sourceId = :sourceId")
    public abstract void updateInstallVersion(long sourceId);

    @Transaction
    public void deleteInstalledSource(long sourceId){
        deleteSearchIndexOfSource(sourceId);
        deleteScheduleOfSource(sourceId);
        deleteSource(sourceId);
    }

    @Query("DELETE FROM search_index "
            + "WHERE rowid IN "
            + "(SELECT id FROM components WHERE sourceId = :sourceId)")
    public abstract void deleteSearchIndexOfSource(long sourceId);

    @Query("DELETE FROM source_schedules "
            + "WHERE sourceId = :sourceId")
    public abstract void deleteScheduleOfSource(long sourceId);

    @Query("DELETE FROM sources "
            + "WHERE id = :sourceId")
    public abstract void deleteSource(long sourceId);

}
