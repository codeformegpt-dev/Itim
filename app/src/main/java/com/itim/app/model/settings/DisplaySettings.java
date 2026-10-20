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
