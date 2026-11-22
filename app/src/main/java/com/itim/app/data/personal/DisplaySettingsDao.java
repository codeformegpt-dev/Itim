package com.itim.app.data.personal;

import androidx.annotation.NonNull;
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
    @NonNull
    LiveData<DisplaySettings> observeDisplaySettings();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @NonNull
    ListenableFuture<Void> insertDisplaySettings(DisplaySettings settings);

    @Upsert
    @NonNull
    ListenableFuture<Void> upsertDisplaySettings(DisplaySettings settings);

}
