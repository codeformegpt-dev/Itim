package com.itim.app.data.personal;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Upsert;

import com.google.common.util.concurrent.ListenableFuture;
import com.itim.app.model.settings.WidgetConfig;
import com.itim.app.model.state.LaunchState;

@Dao
public abstract class AppStateDao {

    @Query("SELECT * "
            + "FROM launch_state "
            + "WHERE id = 1")
    public abstract ListenableFuture<LaunchState> getLaunchState();

    @Upsert
    public abstract ListenableFuture<Void> upsertLaunchState(LaunchState launchState);

    @Query("SELECT * "
            + "FROM widget_configs "
            + "WHERE id = :widgetId")
    public abstract ListenableFuture<WidgetConfig> getWidgetConfig(int widgetId);

    @Upsert
    public abstract ListenableFuture<Void> upsertWidgetConfig(WidgetConfig config);

    @Query("DELETE FROM widget_configs "
            + "WHERE id = :widgetId")
    public abstract ListenableFuture<Void> deleteWidgetConfig(int widgetId);

    @Query("SELECT * "
            + "FROM widget_configs "
            + "ORDER BY id")
    public abstract ListenableFuture<Void> getWidgetConfigs();

    @Transaction
    public void removeSourceReferences(long sourceId){
        updateLaunchStateClearSource(sourceId);
        deleteWidgetConfigsOfSource(sourceId);
    }

    @Query("UPDATE launch_state "
            + "SET sourceId = NULL "
            + "WHERE sourceId = :sourceId")
    abstract void updateLaunchStateClearSource(long sourceId);

    @Query("DELETE FROM widget_configs "
            + "WHERE sourceId = :sourceId")
    abstract void deleteWidgetConfigsOfSource(long sourceId);























}
