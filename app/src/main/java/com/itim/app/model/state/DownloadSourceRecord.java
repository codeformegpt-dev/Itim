package com.itim.app.model.state;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "download_source_records")
public final class DownloadSourceRecord {

    @PrimaryKey
    private final long sourceId;

    public DownloadSourceRecord(long sourceId) {
        this.sourceId = sourceId;
    }

    public long getSourceId() {
        return sourceId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DownloadSourceRecord that = (DownloadSourceRecord) o;
        return sourceId == that.sourceId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sourceId);
    }

    @NonNull
    @Override
    public String toString() {
        return "DownloadSourceRecord{" +
                "sourceId=" + sourceId +
                '}';
    }
}
