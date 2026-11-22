package com.itim.app.data.content;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.itim.app.model.content.Commentary;
import com.itim.app.model.content.Commentator;
import com.itim.app.model.content.Component;
import com.itim.app.model.content.DefaultSourceSchedule;
import com.itim.app.model.content.SchedulePortion;
import com.itim.app.model.content.SourceLevel;
import com.itim.app.model.content.SourceSchedule;
import com.itim.app.model.hebrew.SearchIndex;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * מסד תוכן בזיכרון עם נתוני בסיס משותפים:
 * <ul>
 *   <li>מקור 1 "בראשית" – מותקן (גרסה 1), עם רכיבים, פרשנים, פירושים, רמות, לוחות ואינדקס חיפוש.</li>
 *   <li>מקור 2 "שמות" – בהורדה (לא מותקן), עם רכיבים ושורת אינדקס.</li>
 * </ul>
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 35)
public abstract class ContentDatabaseTestBase {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    protected static final long SRC_INSTALLED = 1L;
    protected static final long SRC_DOWNLOADING = 2L;

    protected static final Component ROOT_1 = new Component(100L, SRC_INSTALLED, null, 0, "בראשית", null);
    protected static final Component LEAF_1A = new Component(101L, SRC_INSTALLED, 100L, 1, "א", "בראשית ברא אור");
    protected static final Component LEAF_1B = new Component(102L, SRC_INSTALLED, 100L, 2, "ב", "ויכלו השמים אור");
    protected static final Component ROOT_2 = new Component(200L, SRC_DOWNLOADING, null, 0, "שמות", null);
    protected static final Component LEAF_2A = new Component(201L, SRC_DOWNLOADING, 200L, 1, "א", "ואלה שמות");

    protected static final Commentator RASHI = new Commentator(1L, SRC_INSTALLED, "רש\"י", true);
    protected static final Commentator IBN_EZRA = new Commentator(2L, SRC_INSTALLED, "אבן עזרא", false);
    protected static final Commentator BASIC_2 = new Commentator(3L, SRC_DOWNLOADING, "אונקלוס", true);

    protected static final Commentary IBN_EZRA_ON_1A = new Commentary(1L, 101L, 2L, null, 1, "פירוש אבן עזרא");
    protected static final Commentary RASHI_ON_1A = new Commentary(2L, 101L, 1L, "בראשית", 0, "פירוש רש\"י");

    protected static final SourceLevel LEVEL_BOOK = new SourceLevel(SRC_INSTALLED, 1, "ספר");
    protected static final SourceLevel LEVEL_CHAPTER = new SourceLevel(SRC_INSTALLED, 2, "פרק");

    protected static final SourceSchedule SCHEDULE_REGULAR = new SourceSchedule(10L, SRC_INSTALLED, "רגיל");
    protected static final SourceSchedule SCHEDULE_FAST = new SourceSchedule(11L, SRC_INSTALLED, "מהיר");

    protected static final LocalDate DAY_1 = LocalDate.of(2026, 10, 18);
    protected static final SchedulePortion PORTION_DAY_1 = new SchedulePortion(1000L, 10L, DAY_1, 101L, 102L, "יום א");
    protected static final SchedulePortion PORTION_DAY_2 = new SchedulePortion(1001L, 10L, DAY_1.plusDays(1), 102L, 102L, null);
    protected static final SchedulePortion PORTION_DAY_10 = new SchedulePortion(1002L, 10L, DAY_1.plusDays(9), 101L, 101L, null);
    protected static final SchedulePortion PORTION_FAST = new SchedulePortion(1100L, 11L, DAY_1, 101L, 102L, null);

    protected ContentDatabase db;

    @Before
    public final void createDb() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), ContentDatabase.class)
                .allowMainThreadQueries()
                .setQueryExecutor(Runnable::run)
                .setTransactionExecutor(Runnable::run)
                .build();
        seed();
    }

    @After
    public final void closeDb() {
        db.close();
    }

    private void seed() {
        SourceDao sourceDao = db.sourceDao();
        InstallDao install = db.installDao();

        // מקור 1: הורדה מלאה והתקנה
        sourceDao.startFirstDownload(SRC_INSTALLED, "בראשית", 1, 3);
        install.insertComponents(Arrays.asList(ROOT_1, LEAF_1A, LEAF_1B));
        install.insertCommentators(Arrays.asList(RASHI, IBN_EZRA));
        install.insertCommentaries(Arrays.asList(IBN_EZRA_ON_1A, RASHI_ON_1A));
        install.insertLevels(Arrays.asList(LEVEL_CHAPTER, LEVEL_BOOK)); // הפוך בכוונה – לבדיקת ORDER BY
        install.insertSchedules(Arrays.asList(SCHEDULE_REGULAR, SCHEDULE_FAST));
        install.insertPortions(Arrays.asList(PORTION_DAY_10, PORTION_DAY_1, PORTION_DAY_2, PORTION_FAST));
        install.insertDefaultSchedule(new DefaultSourceSchedule(SRC_INSTALLED, 10L));
        install.insertSearchIndex(Arrays.asList(
                new SearchIndex(101L, "בראשית ברא אור"),
                new SearchIndex(102L, "ויכלו השמים אור")));
        install.updateInstalledVersion(SRC_INSTALLED);

        // מקור 2: בהורדה, לא הותקן
        sourceDao.startFirstDownload(SRC_DOWNLOADING, "שמות", 1, 2);
        install.insertComponents(Arrays.asList(ROOT_2, LEAF_2A));
        install.insertCommentators(Arrays.asList(BASIC_2));
        install.insertSearchIndex(Arrays.asList(new SearchIndex(201L, "ואלה שמות")));
    }
}
