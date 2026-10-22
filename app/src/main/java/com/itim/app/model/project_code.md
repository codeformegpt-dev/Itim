# Project Code Dump

Root folder: E:\DesktopBackup\DesktopMoved_20260416_215148\david\itim\app\src\main\java\com\itim\app\model
Generated: 2026-10-22 12:49
Total files: 21

## File tree

- content/Commentary.java
- content/Commentator.java
- content/Component.java
- content/Source.java
- content/SourceSchedule.java
- distribution/LibraryItem.java
- distribution/ReleaseStatus.java
- distribution/SourceDownload.java
- hebrew/HebrewDate.java
- hebrew/SearchIndex.java
- hebrew/SearchResult.java
- hebrew/SearchSuggestion.java
- settings/DisplaySettings.java
- settings/FontFamily.java
- settings/TextMarks.java
- settings/WidgetConfig.java
- state/DailySchedule.java
- state/DailyStatus.java
- state/LaunchState.java
- state/Mode.java
- state/Progress.java

---

## File: content/Commentary.java

````java
package com.itim.app.model.content;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(
        tableName = "commentary",
        foreignKeys = {
                        @ForeignKey(
                                entity = Component.class,
                                parentColumns = "id",
                                childColumns = "componentId",
                                onDelete = ForeignKey.CASCADE
                        ),
                        @ForeignKey(
                                entity = Commentator.class,
                                parentColumns = "id",
                                childColumns = "commentatorId",
                                onDelete = ForeignKey.CASCADE
                        )
        },
        indices = {
                    @Index(value = {"componentId", "commentatorId", "orderIndex"}, unique = true),
                    @Index("commentatorId"),
                    @Index("title")
        }
)
public final class Commentary {

    @PrimaryKey
    private final long id;
    private final long componentId;
    private final long commentatorId;
    @Nullable
    private final String title;
    private final int orderIndex;
    @NonNull
    private final String text;

    public Commentary(long id, long componentId, long commentatorId, @Nullable String title, int orderIndex, @NonNull String text) {
        this.id = id;
        this.componentId = componentId;
        this.commentatorId = commentatorId;
        this.title = title;
        this.orderIndex = orderIndex;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public long getComponentId() {
        return componentId;
    }

    public long getCommentatorId() {
        return commentatorId;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    @NonNull
    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Commentary that = (Commentary) o;
        return id == that.id && componentId == that.componentId && commentatorId == that.commentatorId && orderIndex == that.orderIndex && Objects.equals(title, that.title) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, componentId, commentatorId, title, orderIndex, text);
    }

    @NonNull
    @Override
    public String toString() {
        return "Commentary{" +
                "id=" + id +
                ", componentId=" + componentId +
                ", commentatorId=" + commentatorId +
                ", title='" + title + '\'' +
                ", orderIndex=" + orderIndex +
                ", text='" + text + '\'' +
                '}';
    }
}
````

---

## File: content/Commentator.java

````java
package com.itim.app.model.content;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;
//לא כללנו את targumRole משתי סיבות: 1. זה לא נכון. השדות שמגדירים מפרש על ספר לא כוללים אם הוא רש"י או אונקלוס! הם כוללים שם, ID ועוד אבל דבר כז, מה הוא מבין שתי אפשרויות שממש לא רלוונטיות לרוב המוחלט של המפ]רשים, לא יכול להיות שדה אצל כל מפרש. נוסף לכך, 2. יהיו המוני מפרשים ולא הגיוני שכולם יהיו עם שדה ריק. רק בשביל שני מפרשים על מקור אחד שיהיו עם מלא. צריך למצוא פתרון אחר אבל לכאן זה לא נכנס השדה הזה.
@Entity(
        tableName = "commentator",
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
````

---

## File: content/Component.java

````java
package com.itim.app.model.content;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(
        tableName   = "component",
        foreignKeys = {@ForeignKey(
                        entity = Source.class,
                        parentColumns = "id",
                        childColumns = "sourceId",
                        onDelete = ForeignKey.CASCADE
        ),
                        @ForeignKey(
                                entity = Component.class,
                                parentColumns = "id",
                                childColumns = "parentId",
                                onDelete = ForeignKey.CASCADE

                        )},
        indices   = {@Index(value = {"sourceId", "orderIndex"}, unique = true),
                     @Index("parentId")
        }
)
public final class Component {

    @PrimaryKey
    private final long id;
    private final long sourceId;
    @Nullable
    private final Long parentId;
    private final int orderIndex;
    @NonNull
    private final String label;
    @Nullable
    private final String text;

    public Component(long id, long sourceId, @Nullable Long parentId, int orderIndex, @NonNull String label, @Nullable String text){
        this.id = id;
        this.sourceId = sourceId;
        this.parentId = parentId;
        this.orderIndex = orderIndex;
        this.label = label;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public long getSourceId() {
        return sourceId;
    }

    @Nullable
    public Long getParentId() {
        return parentId;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    @NonNull
    public String getLabel() {
        return label;
    }

    @Nullable
    public String getText() {
        return text;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Component component = (Component) o;
        return id == component.id && sourceId == component.sourceId && orderIndex == component.orderIndex && Objects.equals(parentId, component.parentId) && Objects.equals(label, component.label) && Objects.equals(text, component.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sourceId, parentId, orderIndex, label, text);
    }

    @NonNull
    @Override
    public String toString() {
        return "Component{" +
                "id=" + id +
                ", sourceId=" + sourceId +
                ", parentId=" + parentId +
                ", orderIndex=" + orderIndex +
                ", label='" + label + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
````

---

## File: content/Source.java

````java
package com.itim.app.model.content;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(
        tableName = "source",
        indices  = {@Index(value = "label", unique = true)}
)
public final class Source {
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Source that = (Source) o;
        return id == that.id && Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label);
    }

    @NonNull
    @Override
    public String toString() {
        return "Source{" +
                "id=" + id +
                ", label='" + label + '\'' +
                '}';
    }
}
````

---

## File: content/SourceSchedule.java

````java
package com.itim.app.model.content;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Objects;

@Entity(
        tableName = "sourceSchedule",
        foreignKeys = {
                        @ForeignKey(
                                    entity = Source.class,
                                    parentColumns = "id",
                                    childColumns = "sourceId",
                                    onDelete = ForeignKey.CASCADE
                        ),
                        @ForeignKey(
                                    entity = Component.class,
                                    parentColumns = "id",
                                    childColumns = "startComponentId",
                                    onDelete = ForeignKey.RESTRICT
                        ),
                        @ForeignKey(
                                    entity = Component.class,
                                    parentColumns = "id",
                                    childColumns = "endComponentId",
                                    onDelete = ForeignKey.RESTRICT
                )
        },
        indices = {
                @Index(value = {"sourceId", "date"}, unique = true),
                @Index("title")
        }
)
public final class SourceSchedule {

    @PrimaryKey
    private final long id;
    private final long sourceId;
    @NonNull
    private final LocalDate date;
    private final long startComponentId;
    private final long endComponentId;
    @Nullable
    private final String title;

    public SourceSchedule(long id, long sourceId, @NonNull LocalDate date, long startComponentId, long endComponentId, @Nullable String title) {
        this.id = id;
        this.sourceId = sourceId;
        this.date = date;
        this.startComponentId = startComponentId;
        this.endComponentId = endComponentId;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public long getSourceId() {
        return sourceId;
    }

    @NonNull
    public LocalDate getDate() {
        return date;
    }

    public long getStartComponentId() {
        return startComponentId;
    }

    public long getEndComponentId() {
        return endComponentId;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SourceSchedule that = (SourceSchedule) o;
        return id == that.id && sourceId == that.sourceId && startComponentId == that.startComponentId && endComponentId == that.endComponentId && Objects.equals(date, that.date) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sourceId, date, startComponentId, endComponentId, title);
    }

    @NonNull
    @Override
    public String toString() {
        return "SourceSchedule{" +
                "id=" + id +
                ", sourceId=" + sourceId +
                ", date=" + date +
                ", startComponentId=" + startComponentId +
                ", endComponentId=" + endComponentId +
                ", title='" + title + '\'' +
                '}';
    }
}
````

---

## File: distribution/LibraryItem.java

````java
package com.itim.app.model.distribution;

import androidx.annotation.NonNull;

import java.util.Objects;

public final class LibraryItem {

    public enum LibraryState {
        AVAILABLE,
        DOWNLOADING,
        PAUSED,
        INSTALLED
    }

    private final long sourceId;
    private final String label;
    private final LibraryState state;
    private final Integer progressPercent;

    private LibraryItem(long sourceId, String label, LibraryState state, Integer progressPercent) {
        this.sourceId = sourceId;
        this.label = label;
        this.state = state;
        this.progressPercent = progressPercent;

        boolean inProgress = state == LibraryState.DOWNLOADING || state == LibraryState.PAUSED;
        if (inProgress) {
            if (progressPercent == null || progressPercent < 0 || progressPercent > 100)
                throw new IllegalArgumentException("progressPercent must be 0-100 in " + state + ", got " + progressPercent);
        } else if (progressPercent != null)
            throw new IllegalArgumentException("progressPercent must be null in " + state);

    }

    public static LibraryItem of(long sourceId, String label, SourceDownload download, boolean downloadRunning) {
        if (download == null || (download.getInstalledVersion() == null && download.getTargetVersion() == null))
            return new LibraryItem(sourceId, label, LibraryState.AVAILABLE, null);

        if (download.getInstalledVersion() != null)
            return new LibraryItem(sourceId, label, LibraryState.INSTALLED, null);
        
        int percent = download.getCompletedParts() * 100 / download.getTotalParts();
        LibraryState state = downloadRunning ? LibraryState.DOWNLOADING : LibraryState.PAUSED;
        return new LibraryItem(sourceId, label, state, percent);
    }

    public long getSourceId() {
        return sourceId;
    }

    public String getLabel() {
        return label;
    }

    public LibraryState getState() {
        return state;
    }

    public Integer getProgressPercent() {
        return progressPercent;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LibraryItem that = (LibraryItem) o;
        return sourceId == that.sourceId && Objects.equals(label, that.label) && state == that.state && Objects.equals(progressPercent, that.progressPercent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceId, label, state, progressPercent);
    }

    @NonNull
    @Override
    public String toString() {
        return "LibraryItem{" +
                "sourceId=" + sourceId +
                ", label='" + label + '\'' +
                ", state=" + state +
                ", progressPercent=" + progressPercent +
                '}';
    }
}
````

---

## File: distribution/ReleaseStatus.java

````java
package com.itim.app.model.distribution;

public enum ReleaseStatus {

    BUILDING,
    PUBLISHED,
    SUPERSEDED,
    FAILED

}
````

---

## File: distribution/SourceDownload.java

````java
package com.itim.app.model.distribution;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.itim.app.model.content.Source;

import java.time.Instant;
import java.util.Objects;

@Entity(
        tableName = "sourceDownload",
        foreignKeys = @ForeignKey(
                        entity = Source.class,
                        parentColumns = "id",
                        childColumns = "sourceId",
                        onDelete = ForeignKey.CASCADE
                    )
)
public final class SourceDownload {

    @PrimaryKey
    private final long sourceId;
    @Nullable
    private final Integer installedVersion;
    @Nullable
    private final Integer targetVersion;
    @Nullable
    private final Integer totalParts;
    @Nullable
    private final Integer completedParts;
    @Nullable
    private final Instant lastCheckedAt;

    public SourceDownload(long sourceId, @Nullable Integer installedVersion, @Nullable Integer targetVersion, @Nullable Integer totalParts, @Nullable Integer completedParts, @Nullable Instant lastCheckedAt) {
        this.sourceId = sourceId;
        this.installedVersion = installedVersion;
        this.targetVersion = targetVersion;
        this.totalParts = totalParts;
        this.completedParts = completedParts;
        this.lastCheckedAt = lastCheckedAt;
    }

    public long getSourceId() {
        return sourceId;
    }

    @Nullable
    public Integer getInstalledVersion() {
        return installedVersion;
    }

    @Nullable
    public Integer getTargetVersion() {
        return targetVersion;
    }

    @Nullable
    public Integer getTotalParts() {
        return totalParts;
    }

    @Nullable
    public Integer getCompletedParts() {
        return completedParts;
    }

    @Nullable
    public Instant getLastCheckedAt() {
        return lastCheckedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SourceDownload that = (SourceDownload) o;
        return sourceId == that.sourceId && Objects.equals(installedVersion, that.installedVersion) && Objects.equals(targetVersion, that.targetVersion) && Objects.equals(totalParts, that.totalParts) && Objects.equals(completedParts, that.completedParts) && Objects.equals(lastCheckedAt, that.lastCheckedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceId, installedVersion, targetVersion, totalParts, completedParts, lastCheckedAt);
    }

    @NonNull
    @Override
    public String toString() {
        return "SourceDownload{" +
                "sourceId=" + sourceId +
                ", installedVersion=" + installedVersion +
                ", targetVersion=" + targetVersion +
                ", totalParts=" + totalParts +
                ", completedParts=" + completedParts +
                ", lastCheckedAt=" + lastCheckedAt +
                '}';
    }
}
````

---

## File: hebrew/HebrewDate.java

````java
package com.itim.app.model.hebrew;

import java.time.LocalDate;
import java.util.Objects;

import androidx.annotation.NonNull;
import android.icu.util.Calendar;
import android.icu.util.HebrewCalendar;
import android.icu.util.TimeZone;


public final class HebrewDate {

    /** מספר היום היוליאני של 1970-01-01, שהוא יום 0 ב-java.time*/
    private static final int JULIAN_DAY_OF_EPOCH = 2440588;




    public final int year;
    public final int month;
    public final int day;

    public HebrewDate(int year, int month, int day) {
        if (year < 1)
            throw new IllegalArgumentException("year must be >= 1, got " + year);
        if (month < 1 || month > 14)
            throw new IllegalArgumentException("month must be 1-14, got " + month);

        boolean isLeapYear =isLeapYear(year);
        if (isLeapYear ? month == 12 : month >= 13)
            throw new IllegalArgumentException("month " + month + " invalid for " + (isLeapYear ? "leap year" : "non leap year") + ":" + year);

        int lengthOfMonth = lengthOfMonth(year, month);
        if (day < 1 || day > lengthOfMonth)
            throw new IllegalArgumentException("day must be 1-" + lengthOfMonth + ", got " + day);

        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }
    public int getMonth() { return month;}
    public int getDay() { return day; }

    public boolean isLeapYear() {
        return isLeapYear(year);
    }

    public static boolean isLeapYear(int year) {
        int mod19 = year % 19;
        return mod19 == 0 || mod19 == 3 || mod19 == 6 || mod19 == 8 || mod19 == 11 || mod19 == 14 || mod19 == 17;
    }

    public int lengthOfMonth() {
        return lengthOfMonth(year, month);
    }

    /** ה-1 של אותו חודש באותה שנה. */
    @NonNull
    public HebrewDate firstOfMonth() {
        return new HebrewDate(year, month, 1);
    }

    @NonNull
    public static HebrewDate fromGregorian(@NonNull LocalDate gregorianDate) {
        HebrewCalendar cal = newCalendar();
        cal.set(Calendar.JULIAN_DAY, Math.toIntExact(gregorianDate.toEpochDay()) + JULIAN_DAY_OF_EPOCH);

        int year = cal.get(Calendar.YEAR);
        int icuMonth = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return new HebrewDate(year, fromIcuMonth(icuMonth, isLeapYear(year)), day);
    }

    @NonNull
    public LocalDate toGregorian() {
        HebrewCalendar cal = newCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, toIcuMonth(month));
        cal.set(Calendar.DAY_OF_MONTH, day);

        return LocalDate.ofEpochDay(cal.get(Calendar.JULIAN_DAY) - JULIAN_DAY_OF_EPOCH);
    }

    private static HebrewCalendar newCalendar() {
        HebrewCalendar cal = new HebrewCalendar(TimeZone.getTimeZone("GMT"));
        cal.clear();
        return cal;
    }

    /** אורך חודש (29 או 30). מניח שהשנה והחודש כבר נבדקו. */
    private static int lengthOfMonth(int year, int month) {
        HebrewCalendar cal = newCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, toIcuMonth(month));
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /** חודשי ICU: TISHRI=0 ... SHEVAT=4, ADAR_1=5, ADAR=6, NISAN=7 ... ELUL=12 */
    private static int fromIcuMonth(int icuMonth, boolean leapYear) {
        switch (icuMonth) {
            case HebrewCalendar.ADAR_1:
                return 13;                       // אדר א' (רק בשנה מעוברת)
            case HebrewCalendar.ADAR:
                return leapYear ? 14 : 12;       // אדר ב' בשנה מעוברת, אדר רגיל אחרת
            default:
                return icuMonth >= HebrewCalendar.NISAN
                        ? icuMonth - 6           // ניסן..אלול -> 1..6
                        : icuMonth + 7;          // תשרי..שבט -> 7..11
        }
    }

    /** ההפך של fromIcuMonth: המספור שלנו (1-14) לחודש ב-ICU. */
    private static int toIcuMonth(int month) {
        switch (month) {
            case 13:
                return HebrewCalendar.ADAR_1;    // אדר א'
            case 12:                             // אדר (שנה פשוטה)
            case 14:                             // אדר ב' (שנה מעוברת)
                return HebrewCalendar.ADAR;
            default:
                return month >= 7
                        ? month - 7              // תשרי..שבט -> 0..4
                        : month + 6;             // ניסן..אלול -> 7..12
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HebrewDate that = (HebrewDate) o;
        return year == that.year && month == that.month && day == that.day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day);
    }

    @NonNull
    @Override
    public String toString() {
        return "HebrewDate{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }
}
````

---

## File: hebrew/SearchIndex.java

````java
package com.itim.app.model.hebrew;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "searchIndex")
@Fts4
public final class SearchIndex {

    @PrimaryKey
    @ColumnInfo(name = "rowid")
    private final long rowid;
    @NonNull
    private final String normalizedText;

    public SearchIndex(long rowid, @NonNull String normalizedText) {
        this.rowid = rowid;
        this.normalizedText = normalizedText;
    }

    public long getRowid() {
        return rowid;
    }

    @NonNull
    public String getNormalizedText() {
        return normalizedText;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SearchIndex that = (SearchIndex) o;
        return rowid == that.rowid && Objects.equals(normalizedText, that.normalizedText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowid, normalizedText);
    }

    @NonNull
    @Override
    public String toString() {
        final short maxLength = 49;
        final String preview = normalizedText.length() > maxLength ? normalizedText.substring(0, maxLength) : normalizedText;
        return "SearchIndex{" +
                "rowid=" + rowid +
                ", normalizedText=" + preview + '\"' +
                '}';
    }
}
````

---

## File: hebrew/SearchResult.java

````java
package com.itim.app.model.hebrew;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class SearchResult {

    private final long componentId;
    private final long sourceId;
    private final boolean installed;
    @NonNull
    private final List<String> path;
    @NonNull
    private final String snippet;

    public SearchResult(long componentId, long sourceId, boolean installed, @NonNull List<String> path, @NonNull String snippet) {
        this.componentId = componentId;
        this.sourceId = sourceId;
        this.installed = installed;
        this.path = List.copyOf(path);
        this.snippet = Objects.requireNonNull(snippet);
    }

    public long getComponentId() {
        return componentId;
    }

    public long getSourceId() {
        return sourceId;
    }

    public boolean isInstalled() {
        return installed;
    }

    @NonNull
    public List<String> getPath() {
        return path;
    }

    @NonNull
    public String getSnippet() {
        return snippet;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SearchResult that = (SearchResult) o;
        return componentId == that.componentId;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(componentId);
    }

    @NonNull
    @Override
    public String toString() {
        return "SearchResult{" +
                "componentId=" + componentId +
                ", sourceId=" + sourceId +
                ", installed=" + installed +
                ", path=" + path +
                ", snippet='" + snippet + '\'' +
                '}';
    }
}
````

---

## File: hebrew/SearchSuggestion.java

````java
package com.itim.app.model.hebrew;

import androidx.annotation.NonNull;

import java.util.List;

public final class SearchSuggestion {

    private final long componentId;
    private final long sourceId;
    private final boolean installed;
    @NonNull
    private final List<String> path;

    public SearchSuggestion(long componentId, long sourceId, boolean installed, @NonNull List<String> path) {
        this.componentId = componentId;
        this.sourceId = sourceId;
        this.installed = installed;
        this.path = List.copyOf(path);
    }

    public long getComponentId() {
        return componentId;
    }

    public long getSourceId() {
        return sourceId;
    }

    public boolean isInstalled() {
        return installed;
    }

    @NonNull
    public List<String> getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SearchSuggestion that = (SearchSuggestion) o;
        return componentId == that.componentId;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(componentId);
    }

    @NonNull
    @Override
    public String toString() {
        return "SearchSuggestion{" +
                "componentId=" + componentId +
                ", sourceId=" + sourceId +
                ", installed=" + installed +
                ", path=" + path +
                '}';
    }


}
````

---

## File: settings/DisplaySettings.java

````java
package com.itim.app.model.settings;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "displaySettings")
public final class DisplaySettings {

    public static final int SINGLETON_ID = 1;
    public static final int MIN_FONT_SIZE = 14;
    public static final int MAX_FONT_SIZE = 40;
    public static final int FONT_SIZE_STEP = 2;
    public static final int DEFAULT_FONT_SIZE = 20;
    public static final FontFamily DEFAULT_FONT_FAMILY = FontFamily.TAAMEY_FRANK;
    public static final TextMarks DEFAULT_TEXT_MARKS = TextMarks.ALL;


    public static DisplaySettings defaults = new DisplaySettings(SINGLETON_ID, DEFAULT_FONT_SIZE, DEFAULT_FONT_FAMILY, DEFAULT_TEXT_MARKS);


    @PrimaryKey
    private final int id;
    private final int fontSize;
    @NonNull
    private final FontFamily fontFamily;
    @NonNull
    private final TextMarks textMarks;

    public DisplaySettings(int id, int fontSize, @NonNull FontFamily fontFamily, @NonNull TextMarks textMarks) {
        if (id != SINGLETON_ID)
            throw new IllegalArgumentException(
                    "DisplaySettings is a singleton, id must be " + SINGLETON_ID + " but was " + id);
        if (!isValidFontSize(fontSize))
            throw new IllegalArgumentException(
                    "fontSize must be " + MIN_FONT_SIZE + ".." + MAX_FONT_SIZE
                            + " in steps of " + FONT_SIZE_STEP + " but was " + fontSize);
        this.id = id;
        this.fontSize = fontSize;
        this.fontFamily = fontFamily;
        this.textMarks = textMarks;
    }

    public static boolean isValidFontSize(int fontSize) {
        return fontSize >= MIN_FONT_SIZE && fontSize <= MAX_FONT_SIZE && (fontSize - MAX_FONT_SIZE) % FONT_SIZE_STEP == 0;
    }

    public int getId() {
        return id;
    }

    public int getFontSize() {
        return fontSize;
    }

    @NonNull
    public FontFamily getFontFamily() {
        return fontFamily;
    }

    @NonNull
    public TextMarks getTextMarks() {
        return textMarks;
    }

    @NonNull
    public DisplaySettings withFontSize(int fontSize) {
        return new DisplaySettings(id, fontSize, fontFamily, textMarks);
    }

    @NonNull
    public DisplaySettings withFontFamily(@NonNull FontFamily fontFamily) {
        return new DisplaySettings(id, fontSize, fontFamily, textMarks);
    }

    @NonNull
    public DisplaySettings withTextMarks(@NonNull TextMarks textMarks) {
        return new DisplaySettings(id, fontSize, fontFamily, textMarks);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        DisplaySettings that = (DisplaySettings) o;
        return id == that.id && fontSize == that.fontSize && fontFamily == that.fontFamily
                && textMarks == that.textMarks;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fontSize, fontFamily, textMarks);
    }

    @NonNull
    @Override
    public String toString() {
        return "DisplaySettings{" +
                "id=" + id +
                ", fontSize=" + fontSize +
                ", fontFamily=" + fontFamily +
                ", textMarks=" + textMarks +
                '}';
    }
}
````

---

## File: settings/FontFamily.java

````java
package com.itim.app.model.settings;

public enum FontFamily {

    TAAMEY_FRANK,
    EZRA,
    NOTO_SANS

}
````

---

## File: settings/TextMarks.java

````java
package com.itim.app.model.settings;

public enum TextMarks {

    ALL,
    NIKUD,
    NONE

}
````

---

## File: settings/WidgetConfig.java

````java
package com.itim.app.model.settings;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.itim.app.model.content.Source;

import java.util.Objects;

@Entity(
        tableName = "widgetConfig",
        foreignKeys = @ForeignKey(
                entity = Source.class,
                parentColumns = "id",
                childColumns = "sourceId",
                onDelete = ForeignKey.CASCADE
        )
)
public final class WidgetConfig {

    @PrimaryKey
    private final int id;
    private final long sourceId;

    public WidgetConfig(int id, long sourceId) {
        this.id = id;
        this.sourceId = sourceId;
    }

    public int getId() {
        return id;
    }

    public long getSourceId() {
        return sourceId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        WidgetConfig that = (WidgetConfig) o;
        return id == that.id && sourceId == that.sourceId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sourceId);
    }

    @NonNull
    @Override
    public String toString() {
        return "WidgetConfig{" +
                "id=" + id +
                ", sourceId=" + sourceId +
                '}';
    }
}
````

---

## File: state/DailySchedule.java

````java
package com.itim.app.model.state;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.itim.app.model.content.Source;

import java.time.LocalDate;
import java.util.Objects;

@Entity(
        tableName = "dailySchedule",
        foreignKeys = @ForeignKey(
                        entity = Source.class,
                        parentColumns = "id",
                        childColumns = "sourceId",
                        onDelete = ForeignKey.CASCADE
                    ),
        indices = @Index("sourceId")
)
public final class DailySchedule {
    @PrimaryKey(autoGenerate = true)
    private final long id;
    private final long sourceId;
    private final int offsetDays;
    @Nullable
    private final LocalDate lastCompletedDate;

    public DailySchedule(long id, long sourceId, int offsetDays, @Nullable LocalDate lastCompletedDate) {
        this.id = id;
        this.sourceId = sourceId;
        this.offsetDays = offsetDays;
        this.lastCompletedDate = lastCompletedDate;
    }

    public long getId() {
        return id;
    }

    public long getSourceId() {
        return sourceId;
    }

    public int getOffsetDays() {
        return offsetDays;
    }

    @Nullable
    public LocalDate getLastCompletedDate() {
        return lastCompletedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DailySchedule that = (DailySchedule) o;
        return id == that.id && sourceId == that.sourceId && offsetDays == that.offsetDays && Objects.equals(lastCompletedDate, that.lastCompletedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sourceId, offsetDays, lastCompletedDate);
    }

    @NonNull
    @Override
    public String toString() {
        return "DailySchedule{" +
                "id=" + id +
                ", sourceId=" + sourceId +
                ", offsetDays=" + offsetDays +
                ", lastCompletedDate=" + lastCompletedDate +
                '}';
    }


}
````

---

## File: state/DailyStatus.java

````java
package com.itim.app.model.state;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public final class DailyStatus {

    public enum DailyState{
        NO_PORTION,
        NOT_STARTED,
        IN_PROGRESS,
        COMPLETED
    }

    private final long sourceId;
    @NonNull
    private final String label;
    @Nullable
    private final String portionTitle;
    @NonNull
    private final DailyState state;

    public DailyStatus(long sourceId, @NonNull String label, @Nullable String portionTitle, DailyState state) {
        this.sourceId = sourceId;
        this.label = label;
        this.portionTitle = portionTitle;
        this.state = state;
    }

    public long getSourceId() {
        return sourceId;
    }

    @NonNull
    public String getLabel() {
        return label;
    }

    @Nullable
    public String getPortionTitle() {
        return portionTitle;
    }

    public DailyState getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DailyStatus that = (DailyStatus) o;
        return sourceId == that.sourceId && Objects.equals(label, that.label) && Objects.equals(portionTitle, that.portionTitle) && state == that.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceId, label, portionTitle, state);
    }

    @NonNull
    @Override
    public String toString() {
        return "DailyStatus{" +
                "sourceId=" + sourceId +
                ", label='" + label + '\'' +
                ", portionTitle='" + portionTitle + '\'' +
                ", state=" + state +
                '}';
    }
}
````

---

## File: state/LaunchState.java

````java
package com.itim.app.model.state;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.itim.app.model.content.Source;

import java.util.Objects;

@Entity(
        tableName = "launchState",
        foreignKeys = @ForeignKey(
                entity = Source.class,
                parentColumns = "id",
                childColumns = "sourceId"
        ),
        indices = @Index("sourceId")

)
public final class LaunchState {

    @PrimaryKey
    private final long id;
    @Nullable
    private final Mode mode;
    @Nullable
    private final Long sourceId;

    public LaunchState(long id, @Nullable Mode mode, @Nullable Long sourceId) {
        this.id = id;
        this.mode = mode;
        this.sourceId = sourceId;
    }

    public long getId() {
        return id;
    }

    @Nullable
    public Mode getMode() {
        return mode;
    }

    @Nullable
    public Long getSourceId() {
        return sourceId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LaunchState that = (LaunchState) o;
        return id == that.id && mode == that.mode && Objects.equals(sourceId, that.sourceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mode, sourceId);
    }

    @NonNull
    @Override
    public String toString() {
        return "LaunchState{" +
                "id=" + id +
                ", mode=" + mode +
                ", sourceId=" + sourceId +
                '}';
    }
}
````

---

## File: state/Mode.java

````java
package com.itim.app.model.state;

public enum Mode {

    DAILY,
    PERSONAL

}
````

---

## File: state/Progress.java

````java
package com.itim.app.model.state;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.itim.app.model.content.Component;
import com.itim.app.model.content.Source;

import java.util.Objects;

@Entity(
        tableName = "progress",
        foreignKeys = {
                @ForeignKey(
                        entity = Source.class,
                        parentColumns = "id",
                        childColumns = "sourceId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Component.class,
                        parentColumns = "id",
                        childColumns = "componentId",
                        onDelete = ForeignKey.SET_NULL
                )
        },
        indices = {
                @Index(value = {"mode", "sourceId"}, unique = true),
                @Index("componentId")
        }
)
public final class Progress {
    @PrimaryKey(autoGenerate = true)
    private final long id;
    @NonNull
    private final Mode mode;
    private final long sourceId;
    private final long componentId;

    public Progress(long id, @NonNull Mode mode, long sourceId, long componentId) {
        this.id = id;
        this.mode = mode;
        this.sourceId = sourceId;
        this.componentId = componentId;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public Mode getMode() {
        return mode;
    }

    public long getSourceId() {
        return sourceId;
    }

    public long getComponentId() {
        return componentId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Progress progress = (Progress) o;
        return id == progress.id && sourceId == progress.sourceId && componentId == progress.componentId && mode == progress.mode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mode, sourceId, componentId);
    }

    @NonNull
    @Override
    public String toString() {
        return "Progress{" +
                "id=" + id +
                ", mode=" + mode +
                ", sourceId=" + sourceId +
                ", componentId=" + componentId +
                '}';
    }
}
````
