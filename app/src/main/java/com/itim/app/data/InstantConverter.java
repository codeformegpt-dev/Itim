package com.itim.app.data;

import androidx.annotation.Nullable;
import androidx.room.TypeConverter;

import java.time.Instant;

public class InstantConverter {

    @TypeConverter
    @Nullable
    public static Long fromInstant(@Nullable Instant instant) {
        return instant == null ? null : instant.toEpochMilli();
    }

    @TypeConverter
    @Nullable
    public static Instant toInstant(@Nullable Long epochMillis) {
        return epochMillis == null ? null : Instant.ofEpochMilli(epochMillis);
    }
}