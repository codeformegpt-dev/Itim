package com.itim.app.data.personal;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Upsert;

import com.google.common.util.concurrent.ListenableFuture;
import com.itim.app.model.settings.DisplaySettings;

@Dao
public interface DisplaySettingsDao {

    @Query("SELECT * "
            + "FROM display_settings "
            + "WHERE id = " + DisplaySettings.SINGLETON_ID)
    LiveData<DisplaySettings> observeDisplaySettings();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    ListenableFuture<Void> insertDisplaySettings(DisplaySettings settings);

    @Upsert
    ListenableFuture<Void> upsertDisplaySettings(DisplaySettings settings);

}
