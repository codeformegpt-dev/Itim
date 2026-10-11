package com.itim.app.model.distribution;


import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.itim.app.model.content.Source;

import java.time.Instant;
import java.util.Objects;

@Entity(
        tableName = "sourceDownload",
        foreignKeys = @ForeignKey(
                        entity = Source.class,
                        parentColumns = "id",
                        childColumns = "sourceId",
                        onDelete = ForeignKey.CASCADE
                    )
)
public class SourceDownload {

    @PrimaryKey
    private final long sourceId;
    @Nullable
    private final Integer installedVersion;
    @Nullable
    private final Integer targetVersion;
    @Nullable
    private final Integer totalParts;
    @Nullable
    private final Integer completedParts;
    @Nullable
    private final Instant lastCheckedAt;

    public SourceDownload(long sourceId, @Nullable Integer installedVersion, @Nullable Integer targetVersion, @Nullable Integer totalParts, @Nullable Integer completedParts, @Nullable Instant lastCheckedAt) {
        this.sourceId = sourceId;
        this.installedVersion = installedVersion;
        this.targetVersion = targetVersion;
        this.totalParts = totalParts;
        this.completedParts = completedParts;
        this.lastCheckedAt = lastCheckedAt;
    }

    public long getSourceId() {
        return sourceId;
    }

    @Nullable
    public Integer getInstalledVersion() {
        return installedVersion;
    }

    @Nullable
    public Integer getTargetVersion() {
        return targetVersion;
    }

    @Nullable
    public Integer getTotalParts() {
        return totalParts;
    }

    @Nullable
    public Integer getCompletedParts() {
        return completedParts;
    }

    @Nullable
    public Instant getLastCheckedAt() {
        return lastCheckedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SourceDownload that = (SourceDownload) o;
        return sourceId == that.sourceId && Objects.equals(installedVersion, that.installedVersion) && Objects.equals(targetVersion, that.targetVersion) && Objects.equals(totalParts, that.totalParts) && Objects.equals(completedParts, that.completedParts) && Objects.equals(lastCheckedAt, that.lastCheckedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceId, installedVersion, targetVersion, totalParts, completedParts, lastCheckedAt);
    }
}