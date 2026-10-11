package com.itim.app.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.itim.app.model.content.Source;

@Database(entities = {Source.class}, version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase{
    public abstract SourceDao sourceDao();

    private static volatile AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "itim.db")
                            .build();

                }
            }
        }
        return instance;
    }

}
