package com.itim.app.data.content;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Embedded;

import com.itim.app.model.content.Source;
import com.itim.app.model.distribution.SourceDownload;

import java.util.Objects;

public final class SourceWithDownload {

    @Embedded
    @NonNull
    private final Source source;

    @Embedded
    @Nullable
    private final SourceDownload download;

    public SourceWithDownload(@NonNull Source source, @Nullable SourceDownload download) {
        this.source = source;
        this.download = download;
    }

    @NonNull
    public Source getSource() {
        return source;
    }

    @Nullable
    public SourceDownload getDownload() {
        return download;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SourceWithDownload that = (SourceWithDownload) o;
        return Objects.equals(source, that.source) && Objects.equals(download, that.download);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, download);
    }
}
