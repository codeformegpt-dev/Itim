package com.itim.app.data.personal;

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
    ListenableFuture<Void> insertDownloadRecord(DownloadSourceRecord record);

    @Query("DELETE FROM download_source_records "
            + "WHERE sourceId = :sourceId")
    ListenableFuture<Void> deleteDownloadRecord(long sourceId);

    @Query("SELECT * "
            + "FROM download_source_records "
            + "ORDER BY sourceId")
    ListenableFuture<List<DownloadSourceRecord>> getDownloadRecords();

}
