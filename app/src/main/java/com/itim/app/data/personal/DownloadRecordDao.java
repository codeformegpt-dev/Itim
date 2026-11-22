package com.itim.app.data.personal;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.google.common.util.concurrent.ListenableFuture;
import com.itim.app.model.state.DownloadSourceRecord;

import java.util.List;

@Dao
public interface DownloadRecordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @NonNull
    ListenableFuture<Void> insertDownloadRecord(@NonNull DownloadSourceRecord record);

    @Query("DELETE FROM download_source_records "
            + "WHERE sourceId = :sourceId")
    @NonNull
    ListenableFuture<Void> deleteDownloadRecord(long sourceId);

    @Query("SELECT * "
            + "FROM download_source_records "
            + "ORDER BY sourceId")
    @NonNull
    ListenableFuture<List<DownloadSourceRecord>> getDownloadRecords();

}
