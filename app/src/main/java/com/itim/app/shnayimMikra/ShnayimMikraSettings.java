package com.itim.app.shnayimMikra;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;

import com.itim.app.model.settings.DisplaySettings;

import java.util.Objects;

final class ShnayimMikraSettings {

    enum Mikra {
        ONCE,
        TWICE
    }
    enum Targum {
        NONE,
        ONKELOS,
        RASHI,
        BOTH
    }
    enum Placement {
        PRE_VERSE,
        PRE_PORTION
    }

    private static final Mikra DEFAULT_MIKRA = Mikra.TWICE;
    private static final Targum DEFAULT_TARGUM = Targum.ONKELOS;
    private static final Placement DEFAULT_Placement = Placement.PRE_VERSE;
    private static final boolean DEFAULT_REPEAT_LAST_VERSE = true;
    private static final boolean DEFAULT_TRIPLE_VERSES = true;
    private static final int DEFAULT_TARGUM_FONT_SIZE = 18;
    private static final DisplaySettings.FontFamily DEFAULT_TARGUM_FONT = DisplaySettings.FontFamily.NOTO_SANS;

    static final ShnayimMikraSettings DEFAULT = new ShnayimMikraSettings(DEFAULT_MIKRA, DEFAULT_TARGUM, DEFAULT_Placement, DEFAULT_REPEAT_LAST_VERSE, DEFAULT_TRIPLE_VERSES, DEFAULT_TARGUM_FONT_SIZE, DEFAULT_TARGUM_FONT);

    private final Mikra mikra;
    private final Targum targum;
    private final Placement placement;
    private final boolean repeatLastVerse;
    private final boolean tripleVerses;
    private final int targumFontSize;
    private final DisplaySettings.FontFamily targumFont;

    private ShnayimMikraSettings(Mikra mikra, Targum targum, Placement placement, boolean repeatLastVerse, boolean tripleVerses, int targumFontSize, DisplaySettings.FontFamily targumFont) {
        this.mikra = mikra;
        this.targum = targum;
        this.placement = placement;
        this.repeatLastVerse = repeatLastVerse;
        this.tripleVerses = tripleVerses;
        this.targumFontSize = targumFontSize;
        this.targumFont = targumFont;
    }

    @NonNull
    @CheckResult
    ShnayimMikraSettings withMikra(Mikra m) {
        return new ShnayimMikraSettings(m, targum, placement, repeatLastVerse, tripleVerses, targumFontSize, targumFont);
    }
    @NonNull
    @CheckResult
    ShnayimMikraSettings withTargum(Targum t) {
        return new ShnayimMikraSettings(mikra, t, placement, repeatLastVerse, tripleVerses, targumFontSize, targumFont);
    }
    @NonNull
    @CheckResult
    ShnayimMikraSettings withPlacement(Placement p) {
        return new ShnayimMikraSettings(mikra, targum, p, repeatLastVerse, tripleVerses, targumFontSize, targumFont);
    }
    @NonNull
    @CheckResult
    ShnayimMikraSettings withRepeatLastVerse(boolean repeat) {
        return new ShnayimMikraSettings(mikra, targum, placement, repeat, tripleVerses, targumFontSize, targumFont);
    }
    @NonNull
    @CheckResult
    ShnayimMikraSettings withTripleVerses(boolean triple) {
        return new ShnayimMikraSettings(mikra, targum, placement, repeatLastVerse, triple, targumFontSize, targumFont);
    }
    @NonNull
    @CheckResult
    ShnayimMikraSettings withTargumFontSize(int fontSize) {
        return new ShnayimMikraSettings(mikra, targum, placement, repeatLastVerse, tripleVerses, fontSize, targumFont);
    }
    @NonNull
    @CheckResult
    ShnayimMikraSettings withTargumFont(DisplaySettings.FontFamily font) {
        return new ShnayimMikraSettings(mikra, targum, placement, repeatLastVerse, tripleVerses, targumFontSize, font);
    }

    public Mikra getMikra() {
        return mikra;
    }

    public Targum getTargum() {
        return targum;
    }

    public Placement getPlacement() {
        return placement;
    }

    public boolean isRepeatLastVerse() {
        return repeatLastVerse;
    }

    public boolean isTripleVerses() {
        return tripleVerses;
    }

    public int getTargumFontSize() {
        return targumFontSize;
    }

    public DisplaySettings.FontFamily getTargumFont() {
        return targumFont;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ShnayimMikraSettings that = (ShnayimMikraSettings) o;
        return repeatLastVerse == that.repeatLastVerse && tripleVerses == that.tripleVerses && targumFontSize == that.targumFontSize && mikra == that.mikra && targum == that.targum && placement == that.placement && targumFont == that.targumFont;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mikra, targum, placement, repeatLastVerse, tripleVerses, targumFontSize, targumFont);
    }

    @NonNull
    @Override
    public String toString() {
        return "ShnayimMikraSettings{" +
                "mikra=" + mikra +
                ", targum=" + targum +
                ", placement=" + placement +
                ", repeatLastVerse=" + repeatLastVerse +
                ", tripleVerses=" + tripleVerses +
                ", targumFontSize=" + targumFontSize +
                ", targumFont=" + targumFont +
                '}';
    }
}
