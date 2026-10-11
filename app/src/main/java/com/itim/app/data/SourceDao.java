package com.itim.app.data;

import androidx.annotation.Nullable;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.itim.app.model.content.Source;

import java.util.List;

@Dao
public interface SourceDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Source source);

    @Nullable
    @Query("SELECT * FROM source WhERE id = :id")
    Source getById(long id);

    @Nullable
    @Query( "SELECT * FROM source ORDER BY id")
    List<Source> getAll();

}
