package com.itim.app.data.content;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.itim.app.data.InstantConverter;
import com.itim.app.data.LocalDateConverter;
import com.itim.app.model.content.Commentary;
import com.itim.app.model.content.Commentator;
import com.itim.app.model.content.Component;
import com.itim.app.model.content.DefaultSourceSchedule;
import com.itim.app.model.content.SchedulePortion;
import com.itim.app.model.content.Source;
import com.itim.app.model.content.SourceLevel;
import com.itim.app.model.content.SourceSchedule;
import com.itim.app.model.distribution.SourceDownload;
import com.itim.app.model.hebrew.SearchIndex;


@Database(
        entities = {Source.class, Component.class, Commentator.class, Commentary.class, SourceSchedule.class, SchedulePortion.class, DefaultSourceSchedule.class, SourceLevel.class, SourceDownload.class, SearchIndex.class},
        version = 1,
        exportSchema = true)
@TypeConverters({LocalDateConverter.class, InstantConverter.class})
public abstract class ContentDatabase extends RoomDatabase {

    private static volatile ContentDatabase instance;

    @NonNull
    public abstract SourceDao sourceDao();

    @NonNull
    public abstract ComponentDao componentDao();

    @NonNull
    public abstract InstallDao installDao();

    @NonNull
    public abstract SearchDao searchDao();

    @NonNull
    public abstract ScheduleDao scheduleDao();

    @NonNull
    public abstract CommentaryDao commentaryDao();

    @NonNull
    public static ContentDatabase getInstance(@NonNull Context context) {
        if (instance == null)
            synchronized (ContentDatabase.class) {
                if (instance == null )
                    instance = Room.databaseBuilder(context.getApplicationContext(), ContentDatabase.class, "content.db")
                            .build();
            }

        return instance;
    }



}
