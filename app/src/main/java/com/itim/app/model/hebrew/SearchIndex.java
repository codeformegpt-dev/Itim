package com.itim.app.model.hebrew;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "searchIndex")
@Fts4
public final class SearchIndex {

    @PrimaryKey
    @ColumnInfo(name = "rowid")
    private final long rowid;
    @NonNull
    private final String normalizedText;

    public SearchIndex(long rowid, @NonNull String normalizedText) {
        this.rowid = rowid;
        this.normalizedText = normalizedText;
    }

    public long getRowid() {
        return rowid;
    }

    @NonNull
    public String getNormalizedText() {
        return normalizedText;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SearchIndex that = (SearchIndex) o;
        return rowid == that.rowid && Objects.equals(normalizedText, that.normalizedText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowid, normalizedText);
    }

    @NonNull
    @Override
    public String toString() {
        final short maxLength = 49;
        final String preview = normalizedText.length() > maxLength ? normalizedText.substring(0, maxLength) : normalizedText;
        return "SearchIndex{" +
                "rowid=" + rowid +
                ", normalizedText=" + preview + '\"' +
                '}';
    }
}
