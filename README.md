<div align="center" dir="rtl">

# עִתִּים

**אפליקציית אנדרואיד ללימוד תורה יומי**

*"קָבַעְתָּ עִתִּים לַתּוֹרָה?"* (שבת לא, א)

![Platform](https://img.shields.io/badge/platform-Android-3DDC84?logo=android&logoColor=white)
![Language](https://img.shields.io/badge/language-Java-ED8B00?logo=openjdk&logoColor=white)
![Persistence](https://img.shields.io/badge/persistence-Room%20%2B%20SQLite-4285F4)
![Status](https://img.shields.io/badge/status-in%20development-yellow)

</div>

---

<div dir="rtl">

## על האפליקציה

עתים עוזרת ללומד לקבוע זמן קבוע ללימוד תורה בכל יום.
הלומד בוחר מקור, למשל תורה, נ"ך, משנה או מסילת ישרים, ולומד אותו באחד משני מסלולים:

- **לימוד יומי:** הספק קבוע לכל תאריך, כמו כל מי שלומד לפי אותו לוח.
- **לימוד אישי:** בקצב של הלומד, וממשיכים בדיוק מהמקום שבו עצר.

האפליקציה בנויה לריכוז: אין חשבונות, אין התחברות, אין פיד ואין התראות. כל לומד הוא אורח, וכל הלימוד עובד גם בלי רשת.

## יכולות מתוכננות

| | יכולת | פירוט |
|---|---|---|
| 📅 | **הספק יומי** | הספק לכל תאריך, לוח שנה עברי, הצצה בימים אחרים, והצעה עדינה להשלים אחרי ימים שפוספסו |
| 📖 | **קריאה רציפה** | טקסט אחד שנגלל ברצף לכל מקור, והמקום נשמר אוטומטית |
| 💬 | **פירושים** | לחיצה על פסוק פותחת את הפירושים עליו: הבסיסיים שמורים במכשיר, והנוספים נטענים מהשרת |
| 🔍 | **חיפוש** | הצעות לפי שם תוך כדי הקלדה ("ברכות" מציע את מסכת ברכות), חיפוש בטקסט המלא בשרת, וחיפוש בתנ"ך גם בלי רשת |
| ⬇️ | **ספרייה** | הורדת מקורות נוספים בחלקים, עם השהיה והמשך, ועדכון שקט ברקע לגרסאות חדשות |
| 🏠 | **ווידג'ט** | ההספק של היום והמצב שלו (לא התחיל, בעיצומו, הושלם) עבור מקור שבוחרים |
| 🔤 | **הגדרות תצוגה** | שלושה גופנים עבריים, גודל טקסט, וניקוד וטעמים לפי בחירה |
| 📱 | **טלפון חדש, אותו מקום** | המקום וההגדרות משוחזרים מהגיבוי של אנדרואיד, והמקורות יורדים מחדש אוטומטית |

הממשק כולו בעברית, מימין לשמאל, ומתאים את עצמו לערכת הצבעים (בהירה או כהה) של המכשיר.

## ארכיטקטורה

האפליקציה נבנית מלמטה למעלה, שכבה אחר שכבה. כל שכבה תלויה רק בשכבות שמתחתיה.

</div>

```mermaid
flowchart TD
    UI["UI<br/>מסכים וווידג'ט"]
    VM["ViewModel"]
    LOGIC["logic<br/>Repository, עיבוד טקסט עברי, חיפוש"]
    DATA["data<br/>DAO, מסדי Room, TypeConverters"]
    MODEL["model<br/>ישויות, מחלקות ערך, enums"]
    CONTENT[("מסד התוכן")]
    PERSONAL[("המסד האישי<br/>מגובה")]
    SERVER[("שרת<br/>ספרייה, קריאה מקוונת, חיפוש")]

    UI --> VM --> LOGIC --> DATA
    LOGIC <--> SERVER
    DATA --> CONTENT
    DATA --> PERSONAL
    DATA -.-> MODEL
    LOGIC -.-> MODEL
    VM -.-> MODEL

    classDef done fill:#d4edda,stroke:#28a745,color:#155724
    classDef wip fill:#fff3cd,stroke:#d39e00,color:#856404
    classDef planned fill:#e9ecef,stroke:#6c757d,color:#383d41
    class MODEL done
    class DATA wip
    class UI,VM,LOGIC planned
```

<div dir="rtl">

### שני מסדי נתונים מקומיים

הנתונים במכשיר מחולקים לשני מסדי SQLite, ש-Room מנהל:

| מסד | מה יש בו | גיבוי של אנדרואיד | למה |
|---|---|---|---|
| **מסד התוכן** | המקורות, עץ הטקסט שלהם, הפירושים, לוחות ההספק, מצב ההורדות ואינדקס החיפוש | לא מגובה | תמיד אפשר להוריד אותו מחדש, והוא גדול ממגבלת הגיבוי האוטומטי (25 MB) |
| **המסד האישי** | המקום בלימוד, התאריכים של הלומד, הגדרות התצוגה, הווידג'טים ורשימת המקורות שהורדו | מגובה | נוצר במכשיר ואי אפשר לשחזר אותו מחדש, ולכן הוא מה שעובר לטלפון חדש |

אין מפתחות זרים בין שני המסדים. הנתונים האישיים מפנים לתוכן לפי מזהים יציבים, שהשרת מקצה ולעולם לא ממחזר. לכן מי שמוחק מקור ומוריד אותו שוב ממשיך מאותו מקום.

### מבנה מסד התוכן

</div>

```mermaid
erDiagram
    source ||--o{ component : "has"
    component |o--o{ component : "parent of"
    source ||--o{ commentator : "has"
    component ||--o{ commentary : "has"
    commentator ||--o{ commentary : "writes"
    source ||--o{ source_schedule : "has"
    component ||--o{ source_schedule : "starts / ends"
    source ||--o| source_download : "tracks"

    source {
        long id PK
        String label UK
    }
    component {
        long id PK
        long sourceId FK
        Long parentId FK "ריק בשורש"
        int orderIndex "סדר הקריאה במקור"
        String label
        String text "ריק ברכיב שאינו עלה"
    }
    commentator {
        long id PK
        long sourceId FK
        String name
        boolean isBasic "שמור במכשיר"
    }
    commentary {
        long id PK
        long componentId FK
        long commentatorId FK
        String title "דיבור המתחיל"
        int orderIndex
        String text
    }
    source_schedule {
        long id PK
        long sourceId FK
        LocalDate date
        long startComponentId FK
        long endComponentId FK
        String title
    }
    source_download {
        long sourceId PK
        Integer installedVersion
        Integer targetVersion
        Integer totalParts
        Integer completedParts
        Instant lastCheckedAt
    }
    search_index {
        long rowid PK "מזהה הרכיב"
        String normalizedText
    }
```

<div dir="rtl">

### מבנה המסד האישי

</div>

```mermaid
erDiagram
    download_source_record {
        long SourceId PK
    }
    progress {
        long id PK
        Mode mode "DAILY או PERSONAL"
        long sourceId
        long componentId
    }
    daily_schedule {
        long id PK
        long sourceId
        int offsetDays
        LocalDate lastCompletedDate
    }
    launch_state {
        long id PK
        Mode mode
        Long sourceId
    }
    display_settings {
        int id PK
        int fontSize
        FontFamily fontFamily
        TextMarks textMarks
    }
    widget_config {
        int id PK "appWidgetId"
        long sourceId
    }
```

<div dir="rtl">

## נקודות הנדסיות

- **מודל בלתי משתנה.** כל המחלקות `final`, כל השדות `final`, ואין אף setter. Room בונה כל שורה דרך הבנאי, ולכן הבדיקות שבבנאי רצות גם בכל קריאה מהמסד, ולא רק בכתיבה.
- **עץ אחד לכל ספר.** כל מבנה, ספר, פרק, פסוק, משנה או סעיף, נשמר כעץ אחד של `Component`, שבו כל רכיב מכיר את האב שלו ואת מקומו בסדר הקריאה. מקור חדש נוסף כנתונים בלבד, בלי שורת קוד, ושאילתה אחת שולפת קטע רציף יחד עם הכותרות שלו.
- **לוח עברי בלי בלבול.** `HebrewDate` עוטפת את `HebrewCalendar` של ICU באנדרואיד ומסתירה את מספור החודשים המבלבל שלו (אדר א' תופס מקום גם בשנה רגילה) מאחורי מספור אחד וברור: חודשים 1 עד 14, מניסן.
- **חיפוש בלי רשת.** טבלת FTS4 של SQLite שומרת עותק מנורמל של הטקסט, בלי ניקוד, בלי טעמים ובלי אותיות סופיות, כך ש"בְּרֵאשִׁית" ו"בראשית" נמצאים באותו חיפוש.
- **אינדקסים שנמדדו.** כל עמודת מפתח זר מכוסה באינדקס. בניסוי על SQLite, מחיקת מקור של כ-30,000 רכיבים ארכה 14 שניות בלי האינדקסים על טבלת לוח ההספק, ו-0.11 שניות איתם.
- **פיתוח לפי אפיון.** כל שכבה נבנית מאפיון כתוב ומיומן החלטות. העבודה מחולקת לשלושה תפקידים נפרדים, החלטה, אפיון ותכנות, כך שאף שורת קוד לא נכתבת כל עוד שאלת תכנון עדיין פתוחה.

## סטטוס

| שכבה | מצב |
|---|---|
| model: ‏13 ישויות, 5 מחלקות ערך, 6 enums | ✅ הושלמה ונסגרה |
| data: ‏DAO, מסדי Room, TypeConverters | 🚧 בעבודה |
| logic: ‏Repository, עיבוד טקסט, חיפוש | ⏳ מתוכננת |
| ViewModel | ⏳ מתוכננת |
| UI: מסכים ו-ווידג'ט | ⏳ מתוכננת |

## מבנה הקוד

שכבת ה-model נמצאת בחבילה `com.itim.app.model`, וכל תחום הוא תת-חבילה משלו:

| חבילה | השאלה שהיא עונה עליה | מחלקות |
|---|---|---|
| `content` | מה לומדים | `Source`, `Component`, `Commentator`, `Commentary`, `SchedulePortion` |
| `distribution` | איך התוכן מגיע למכשיר | `SourceDownload`, `LibraryItem`, `ReleaseStatus` |
| `state` | איפה הלומד נמצא | `Progress`, `DailySchedule`, `LaunchState`, `DownloadSourceRecord`, `DailyStatus`, `Mode` |
| `settings` | איך הטקסט מוצג | `DisplaySettings`, `WidgetConfig`, `FontFamily`, `TextMarks` |
| `hebrew` | תאריך עברי וחיפוש | `HebrewDate`, `SearchIndex`, `SearchResult`, `SearchSuggestion` |

## טכנולוגיות

- **שפה:** Java
- **פלטפורמה:** Android
- **אחסון:** Room מעל SQLite, כולל FTS4 לחיפוש בטקסט מלא
- **תאריכים:** `java.time` ו-ICU של אנדרואיד (`android.icu.util.HebrewCalendar`)
- **שרת:** Supabase, לספריית המקורות, לקריאה מקוונת, לפירושים ולחיפוש בטקסט מלא

</div>

<!--
## צילומי מסך

להוסיף כאן כשהממשק יהיה מוכן. קבצים מוצעים:
docs/images/daily.png, docs/images/calendar.png, docs/images/search.png, docs/images/widget.png

<div align="center">
  <img src="docs/images/daily.png" width="220" alt="הספק יומי" />
  <img src="docs/images/calendar.png" width="220" alt="לוח שנה עברי" />
  <img src="docs/images/search.png" width="220" alt="חיפוש" />
  <img src="docs/images/widget.png" width="220" alt="ווידג'ט" />
</div>
-->
