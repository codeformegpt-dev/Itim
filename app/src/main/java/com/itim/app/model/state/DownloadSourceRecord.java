package com.itim.app.model.state;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "download_source_record")
public final class DownloadSourceRecord {

    @PrimaryKey
    private final long SourceId;

    public DownloadSourceRecord(long sourceId) {
        SourceId = sourceId;
    }

    public long getSourceId() {
        return SourceId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DownloadSourceRecord that = (DownloadSourceRecord) o;
        return SourceId == that.SourceId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(SourceId);
    }

    @NonNull
    @Override
    public String toString() {
        return "DownloadSourceRecord{" +
                "SourceId=" + SourceId +
                '}';
    }
}
