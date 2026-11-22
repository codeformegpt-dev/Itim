package com.itim.app.data.content;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

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
    public abstract void insertCommentators(@NonNull List<Commentator> rows);
    @Insert
    public abstract void insertComponents(@NonNull List<Component> rows);
    @Insert
    public abstract void insertCommentaries(@NonNull List<Commentary> rows);
    @Insert
    public abstract void insertLevels(@NonNull List<SourceLevel> rows);
    @Insert
    public abstract void insertSchedules(@NonNull List<SourceSchedule> rows);
    @Insert
    public abstract void insertPortions(@NonNull List<SchedulePortion> rows);
    @Insert
    public abstract void insertDefaultSchedule(@NonNull DefaultSourceSchedule row);
    @Insert
    public abstract void insertSearchIndex(@NonNull List<SearchIndex> rows);

    @Query("UPDATE source_downloads "
            + "SET installedVersion  = targetVersion, targetVersion = NULL, totalParts = NULL, completedParts = NULL "
            + "WHERE sourceId = :sourceId")
    public abstract void updateInstalledVersion(long sourceId);

    @Transaction
    public void deleteInstalledSource(long sourceId){
        deleteSearchIndexOfSource(sourceId);
        deleteSchedulesOfSource(sourceId);
        deleteSource(sourceId);
    }

    @Query("DELETE FROM search_index "
            + "WHERE rowid IN "
            + "(SELECT id FROM components WHERE sourceId = :sourceId)")
    abstract void deleteSearchIndexOfSource(long sourceId);

    @Query("DELETE FROM source_schedules "
            + "WHERE sourceId = :sourceId")
    abstract void deleteSchedulesOfSource(long sourceId);

    @Query("DELETE FROM sources "
            + "WHERE id = :sourceId")
    abstract void deleteSource(long sourceId);

}
