package com.itim.app.data.personal;

import static com.itim.app.data.TestUtil.await;
import static com.itim.app.data.TestUtil.getValue;
import static org.junit.Assert.*;

import com.itim.app.model.settings.DisplaySettings;
import com.itim.app.model.settings.FontFamily;
import com.itim.app.model.settings.TextMarks;
import com.itim.app.model.state.DownloadSourceRecord;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

/** DisplaySettingsDao ו-DownloadRecordDao. */
public class SettingsAndRecordsDaoTest extends PersonalDatabaseTestBase {

    // ---------- DisplaySettingsDao ----------

    @Test
    public void displaySettingsMissingIsNull() {
        assertNull(getValue(db.displaySettingsDao().observeDisplaySettings()));
    }

    @Test
    public void insertDisplaySettings() {
        await(db.displaySettingsDao().insertDisplaySettings(DisplaySettings.defaults));
        assertEquals(DisplaySettings.defaults, getValue(db.displaySettingsDao().observeDisplaySettings()));
    }

    @Test
    public void insertDisplaySettingsIgnoresWhenExists() {
        DisplaySettings custom = DisplaySettings.defaults.withFontSize(30);
        await(db.displaySettingsDao().upsertDisplaySettings(custom));
        await(db.displaySettingsDao().insertDisplaySettings(DisplaySettings.defaults));
        assertEquals(custom, getValue(db.displaySettingsDao().observeDisplaySettings()));
    }

    @Test
    public void upsertDisplaySettingsReplaces() {
        await(db.displaySettingsDao().insertDisplaySettings(DisplaySettings.defaults));
        DisplaySettings changed = DisplaySettings.defaults
                .withFontFamily(FontFamily.EZRA)
                .withTextMarks(TextMarks.NIKUD);
        await(db.displaySettingsDao().upsertDisplaySettings(changed));
        assertEquals(changed, getValue(db.displaySettingsDao().observeDisplaySettings()));
    }

    // ---------- DownloadRecordDao ----------

    @Test
    public void downloadRecordsEmpty() {
        assertTrue(await(db.downloadRecordDao().getDownloadRecords()).isEmpty());
    }

    @Test
    public void downloadRecordsOrderedAndDuplicatesIgnored() {
        await(db.downloadRecordDao().insertDownloadRecord(new DownloadSourceRecord(5L)));
        await(db.downloadRecordDao().insertDownloadRecord(new DownloadSourceRecord(2L)));
        await(db.downloadRecordDao().insertDownloadRecord(new DownloadSourceRecord(5L)));
        assertEquals(Arrays.asList(new DownloadSourceRecord(2L), new DownloadSourceRecord(5L)),
                await(db.downloadRecordDao().getDownloadRecords()));
    }

    @Test
    public void deleteDownloadRecord() {
        await(db.downloadRecordDao().insertDownloadRecord(new DownloadSourceRecord(2L)));
        await(db.downloadRecordDao().insertDownloadRecord(new DownloadSourceRecord(5L)));
        await(db.downloadRecordDao().deleteDownloadRecord(2L));
        assertEquals(Collections.singletonList(new DownloadSourceRecord(5L)),
                await(db.downloadRecordDao().getDownloadRecords()));
    }
}
