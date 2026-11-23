package com.itim.app; // ⚠️ להחליף בחבילה שבה הקובץ יושב אצלך תחת androidTest/java

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.itim.app.data.content.ContentDatabase;
import com.itim.app.model.content.Component;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * בדיקה על נתונים אמיתיים: content.db מ-androidTest/assets (מסילת ישרים, נ"ך, משנה).
 * לא נוגעת ב-ContentDatabase.getInstance ולא ב-content.db של האפליקציה (DAO-11).
 */
@RunWith(AndroidJUnit4.class)               // "הרץ אותי על אנדרואיד" (אמולטור או טלפון)
public class RealDataTest {

    private static final String TAG = "ITIM";               // לסינון ב-Logcat: tag:ITIM
    private static final String TEST_DB = "test-content.db"; // שם נפרד, לא מתנגש עם content.db

    private static final long MESILAT_YESHARIM = 1L;
    private static final long NACH = 2L;

    private ContentDatabase db;

    @Before                                  // רץ לפני כל @Test
    public void openDatabase() {
        // Context של הבדיקה (לא getTargetContext), כי content.db נמצא ב-assets של הבדיקה
        Context context = InstrumentationRegistry.getInstrumentation().getContext();

        // createFromAsset מעתיק רק כשאין מסד בשם הזה, לכן מוחקים את מה שנשאר מריצה קודמת
        context.deleteDatabase(TEST_DB);

        db = Room.databaseBuilder(context, ContentDatabase.class, TEST_DB)
                .createFromAsset("content.db")
                .build();
    }

    @After                                   // רץ אחרי כל @Test, גם כשהבדיקה נכשלה
    public void closeDatabase() {
        db.close();
    }

    @Test
    public void mesilatYesharim_has27Headings() throws Exception {
        // get() מחכה לתוצאה של ה-ListenableFuture. בבדיקה מותר; במסך אסור.
        List<Component> headings =
                db.componentDao().getNonLeafComponents(MESILAT_YESHARIM).get();

        for (Component heading : headings) {
            Log.d(TAG, heading.getOrderIndex() + ": " + heading.getLabel());
        }

        assertEquals(27, headings.size());               // הקדמה + 26 פרקים
        assertEquals("הקדמה", headings.get(0).getLabel());
    }

    @Test
    public void nach_firstLeafIsJoshua1_1() throws Exception {
        Component firstVerse = db.componentDao().getFirstLeafFrom(NACH, 1).get();

        assertNotNull(firstVerse);                       // null = לא נמצא עלה
        Log.d(TAG, firstVerse.getLabel() + ": " + firstVerse.getText());

        assertEquals("פסוק א'", firstVerse.getLabel());
        assertEquals(3, firstVerse.getOrderIndex());     // 1 = ספר יהושע, 2 = פרק א', 3 = פסוק א'
    }
}