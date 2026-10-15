package com.itim.app.model.hebrew;

import androidx.annotation.NonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class HebrewDateFormatter {

    private static final Map<Integer, Character> GEMATRIA;
    private static final int[] GEMATRIA_VALUES =
            {400, 300, 200, 100, 90, 80, 70, 60, 50, 40, 30, 20, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    private static final Map<Integer, String> HEBREW_MONTH;
    static {
        Map<Integer,Character> g = new HashMap<>();
        g.put(1, 'א');
        g.put(2, 'ב');
        g.put(3, 'ג');
        g.put(4, 'ד');
        g.put(5, 'ה');
        g.put(6, 'ו');
        g.put(7, 'ז');
        g.put(8, 'ח');
        g.put(9, 'ט');
        g.put(10, 'י');
        g.put(20, 'כ');
        g.put(30, 'ל');
        g.put(40, 'מ');
        g.put(50, 'נ');
        g.put(60, 'ס');
        g.put(70, 'ע');
        g.put(80, 'פ');
        g.put(90, 'צ');
        g.put(100, 'ק');
        g.put(200, 'ר');
        g.put(300, 'ש');
        g.put(400, 'ת');
        GEMATRIA = Collections.unmodifiableMap(g);

        Map<Integer,String> m = new HashMap<>();
        m.put(1, "ניסן");
        m.put(2, "אייר");
        m.put(3, "סיון");
        m.put(4, "תמוז");
        m.put(5, "אב");
        m.put(6, "אלול");
        m.put(7, "תשרי");
        m.put(8, "חשון");
        m.put(9, "כסלו");
        m.put(10, "טבת");
        m.put(11, "שבט");
        m.put(12, "אדר");
        m.put(13, "אדר א'");
        m.put(14, "אדר ב'");
        HEBREW_MONTH = Collections.unmodifiableMap(m);

    }

    /** למשל: כ"ז בתשרי תשפ"ז */
    @NonNull
    public static String format(@NonNull HebrewDate date) {
        return formatDay(date) + " ב" + formatMonth(date);
    }
    @NonNull
    public static String fullFormat(@NonNull HebrewDate date) {
        return formatDay(date) + " ב" + formatMonth(date) + " " + formatYear(date);
    }


    /** 3 -> ג', 15 -> ט"ו, 27 -> כ"ז */
    @NonNull
    public static String formatDay(@NonNull HebrewDate date) {
        return toGematria(date.getDay());
    }

    @NonNull
    public static String formatMonth(@NonNull HebrewDate date) {
        return HEBREW_MONTH.get(date.getMonth());
    }

    /** בלי האלפים: 5787 -> תשפ"ז. לא נתמכות שנים שהן כפולות של 1000. */
    @NonNull
    public static String formatYear(@NonNull HebrewDate date) {
        return toGematria(date.getYear() % 1000);
    }

    /**
     * מספר 1-999 לאותיות, עם גרש (אות אחת) או גרשיים לפני האות האחרונה.
     * 15 ו-16 נכתבים ט"ו וט"ז ולא י"ה וי"ו.
     */
    @NonNull
    static String toGematria(int n) {
        if (n < 1 || n > 999)
            throw new IllegalArgumentException("n must be 1-999, got " + n);

        StringBuilder sb = new StringBuilder();
        int rest = n;
        for (int value : GEMATRIA_VALUES) {
            if (rest == 15 || rest == 16) {
                sb.append('ט').append(rest == 15 ? 'ו' : 'ז');
                rest = 0;
                break;
            }
            while (rest >= value) {
                sb.append(GEMATRIA.get(value));
                rest -= value;
            }
        }

        if (sb.length() == 1)
            sb.append('\'');
        else
            sb.insert(sb.length() - 1, '"');

        return sb.toString();
    }
}
