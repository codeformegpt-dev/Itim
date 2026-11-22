package com.itim.app.data.personal;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.itim.app.data.InstantConverter;
import com.itim.app.data.LocalDateConverter;
import com.itim.app.data.content.ContentDatabase;
import com.itim.app.model.settings.DisplaySettings;
import com.itim.app.model.settings.WidgetConfig;
import com.itim.app.model.state.DailyAmountPace;
import com.itim.app.model.state.DailySchedule;
import com.itim.app.model.state.DownloadSourceRecord;
import com.itim.app.model.state.EndDatePace;
import com.itim.app.model.state.FixedPace;
import com.itim.app.model.state.LaunchState;
import com.itim.app.model.state.Progress;

import java.util.List;

@Database(
        entities = {DisplaySettings.class, WidgetConfig.class, DailyAmountPace.class, DailySchedule.class, DownloadSourceRecord.class, EndDatePace.class, FixedPace.class, LaunchState.class, Progress.class},
        version = 1,
        exportSchema = true
)
@TypeConverters({LocalDateConverter.class, InstantConverter.class})
public abstract class PersonalDatabase extends RoomDatabase {

    private static volatile PersonalDatabase instance;

    @NonNull
    public abstract ProgressDao progressDao();

    @NonNull
    public abstract DownloadRecordDao downloadRecordDao();

    @NonNull
    public abstract DisplaySettingsDao displaySettingsDao();

    @NonNull
    public abstract DailyTrackDao dailyTrackDao();

    @NonNull
    public abstract AppStateDao appStateDao();

    @NonNull
    public static PersonalDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (PersonalDatabase.class) {
                if (instance == null) {
                    final Context appContext = context.getApplicationContext();
                    instance = Room.databaseBuilder(appContext, PersonalDatabase.class, "personal.db")
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    List<Long> ids = ContentDatabase.getInstance(appContext).sourceDao().getInstalledSourceIds();
                                    for (Long id :ids) {
                                        db.execSQL("INSERT INTO download_source_records (sourceId) VALUES (?)", new  Object[]{id});
                                    }
                                }
                            })
                            .build();
                }
            }
        }
        return instance;
    }



}
