package com.itim.app.data.content;

import androidx.annotation.NonNull;
import androidx.room.Database;
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
        entities = {Commentary.class, Commentator.class, Component.class, DefaultSourceSchedule.class, SchedulePortion.class, Source.class, SourceLevel.class, SourceSchedule.class, SourceDownload.class, SearchIndex.class},
        version = 1)
@TypeConverters({LocalDateConverter.class, InstantConverter.class})
public abstract class ContentDatabase extends RoomDatabase {

    @NonNull
    public abstract SourceDao sourceDao();

    @NonNull
    public abstract ComponentDao componentDao();

}
