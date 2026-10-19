package com.itim.app.shnayimMikra;

import androidx.annotation.NonNull;

import java.util.Objects;
import java.util.Set;

final class ShnayimMikraData {

    public static final ShnayimMikraData EMPTY = new ShnayimMikraData(null, null);

    private final Set<Long> parshaEndVerseIds;
    private final Set<Long> tripleVerseIds;

    public ShnayimMikraData(Set<Long> parshaEndVerseIds, Set<Long> tripleVerseIds) {
        this.parshaEndVerseIds = Set.copyOf(parshaEndVerseIds);
        this.tripleVerseIds = Set.copyOf(tripleVerseIds);
    }

    public boolean isParshaEnd(long verseId) {
        return parshaEndVerseIds.contains(verseId);
    }

    public boolean isTriple(long verseId) {
        return tripleVerseIds.contains(verseId);
    }

    public Set<Long> getParshaEndVerseIds() {
        return parshaEndVerseIds;
    }

    public Set<Long> getTripleVerseIds() {
        return tripleVerseIds;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ShnayimMikraData that = (ShnayimMikraData) o;
        return Objects.equals(parshaEndVerseIds, that.parshaEndVerseIds) && Objects.equals(tripleVerseIds, that.tripleVerseIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parshaEndVerseIds, tripleVerseIds);
    }

    @NonNull
    @Override
    public String toString() {
        return "ShnayimMikraData{" +
                "parshaEndVerseIds=" + parshaEndVerseIds +
                ", tripleVerseIds=" + tripleVerseIds +
                '}';
    }
}
