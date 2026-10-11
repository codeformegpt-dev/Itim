package com.itim.app.model.content;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "source",
        indices  = {@Index(value = "label", unique = true)}
)
public class Source {
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
}
