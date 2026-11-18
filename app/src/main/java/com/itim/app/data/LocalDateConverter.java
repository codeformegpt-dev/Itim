package com.itim.app.data;

import androidx.annotation.Nullable;
import androidx.room.TypeConverter;

import java.time.LocalDate;

public class LocalDateConverter {

    @TypeConverter
    @Nullable
    public static Long fromLocalDate(@Nullable LocalDate date) {
        return date == null ? null : date.toEpochDay();
    }

    @TypeConverter
    @Nullable
    public static LocalDate toLocalDate(@Nullable Long epochDay) {
        return epochDay == null ? null : LocalDate.ofEpochDay(epochDay);
    }
}