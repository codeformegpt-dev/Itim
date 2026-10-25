package com.itim.app.model.content;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;
@Entity(
        tableName = "commentators",
        foreignKeys = @ForeignKey(
                        entity = Source.class,
                        parentColumns = "id",
                        childColumns = "sourceId",
                        onDelete = ForeignKey.CASCADE),
        indices = {
                    @Index(value = {"sourceId", "name"}, unique = true),
        }
)
public final class Commentator {

    @PrimaryKey
    private final long id;
    private final long sourceId;
    @NonNull
    private final String name;
    private final boolean isBasic;

    public Commentator(long id, long sourceId, @NonNull String name, boolean isBasic) {
        this.id = id;
        this.sourceId = sourceId;
        this.name = name;
        this.isBasic = isBasic;
    }

    public long getId() {
        return id;
    }

    public long getSourceId() {
        return sourceId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public boolean isBasic() {
        return isBasic;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Commentator that = (Commentator) o;
        return id == that.id && sourceId == that.sourceId && isBasic == that.isBasic && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sourceId, name, isBasic);
    }

    @NonNull
    @Override
    public String toString() {
        return "Commentator{" +
                "id=" + id +
                ", sourceId=" + sourceId +
                ", name='" + name + '\'' +
                ", isBasic=" + isBasic +
                '}';
    }
}
