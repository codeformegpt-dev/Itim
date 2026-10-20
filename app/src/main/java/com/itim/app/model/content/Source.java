package com.itim.app.model.content;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(
        tableName = "source",
        indices  = {@Index(value = "label", unique = true)}
)
public final class Source {
    @PrimaryKey
    private final long id;

    @NonNull
    private final String label;

    public Source(long id, @NonNull String label){
    this.id = id;
    this.label = label;
    }

    public long getId(){
    return id;
    }

    @NonNull
    public String getLabel(){
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Source that = (Source) o;
        return id == that.id && Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label);
    }

    @NonNull
    @Override
    public String toString() {
        return "Source{" +
                "id=" + id +
                ", label='" + label + '\'' +
                '}';
    }
}
