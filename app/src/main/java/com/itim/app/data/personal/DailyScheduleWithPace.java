package com.itim.app.data.personal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Embedded;
import androidx.room.Relation;

import com.itim.app.model.state.DailyAmountPace;
import com.itim.app.model.state.DailySchedule;
import com.itim.app.model.state.EndDatePace;
import com.itim.app.model.state.FixedPace;
import com.itim.app.model.state.StudyPace;

import java.util.Objects;


public final class DailyScheduleWithPace {

    @Embedded
    @NonNull
    private final DailySchedule dailySchedule;

    @Relation(parentColumn = "id", entityColumn = "dailyScheduleId")
    @Nullable
    private final FixedPace fixedPace;

    @Relation(parentColumn = "id", entityColumn = "dailyScheduleId")
    @Nullable
    private final EndDatePace endDatePace;

    @Relation(parentColumn = "id", entityColumn = "dailyScheduleId")
    @Nullable
    private final DailyAmountPace dailyAmountPace;

    public DailyScheduleWithPace(@NonNull DailySchedule dailySchedule, @Nullable FixedPace fixedPace, @Nullable EndDatePace endDatePace, @Nullable DailyAmountPace dailyAmountPace) {
        this.dailySchedule = dailySchedule;
        this.fixedPace = fixedPace;
        this.endDatePace = endDatePace;
        this.dailyAmountPace = dailyAmountPace;
    }

    @NonNull
    public DailySchedule getDailySchedule() {
        return dailySchedule;
    }

    @Nullable
    public FixedPace getFixedPace() {
        return fixedPace;
    }

    @Nullable
    public EndDatePace getEndDatePace() {
        return endDatePace;
    }

    @Nullable
    public DailyAmountPace getDailyAmountPace() {
        return dailyAmountPace;
    }

    @Nullable
    public StudyPace getPace() {
        StudyPace pace = null;
        int count = 0;
        if (fixedPace != null) {
            pace = fixedPace;
            count++;
        }
        if (endDatePace != null) {
            pace = endDatePace;
            count++;
        }
        if (dailyAmountPace != null) {
            pace = dailyAmountPace;
            count++;
        }
        return count == 1 ? pace : null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DailyScheduleWithPace that = (DailyScheduleWithPace) o;
        return Objects.equals(dailySchedule, that.dailySchedule) && Objects.equals(fixedPace, that.fixedPace) && Objects.equals(endDatePace, that.endDatePace) && Objects.equals(dailyAmountPace, that.dailyAmountPace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dailySchedule, fixedPace, endDatePace, dailyAmountPace);
    }
}


