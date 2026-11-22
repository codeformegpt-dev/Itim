package com.itim.app.data.personal;

import static com.itim.app.data.TestUtil.await;
import static org.junit.Assert.*;

import com.itim.app.model.settings.WidgetConfig;
import com.itim.app.model.state.LaunchState;
import com.itim.app.model.state.Mode;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class AppStateDaoTest extends PersonalDatabaseTestBase {

    // ---------- LaunchState ----------

    @Test
    public void launchStateMissingIsNull() {
        assertNull(await(db.appStateDao().getLaunchState()));
    }

    @Test
    public void upsertLaunchStateInsertsAndReplaces() {
        await(db.appStateDao().upsertLaunchState(new LaunchState(1L, Mode.DAILY, 3L)));
        assertEquals(new LaunchState(1L, Mode.DAILY, 3L), await(db.appStateDao().getLaunchState()));

        await(db.appStateDao().upsertLaunchState(new LaunchState(1L, Mode.PERSONAL, null)));
        assertEquals(new LaunchState(1L, Mode.PERSONAL, null), await(db.appStateDao().getLaunchState()));
    }

    // ---------- WidgetConfig ----------

    @Test
    public void widgetConfigCrud() {
        assertNull(await(db.appStateDao().getWidgetConfig(7)));

        await(db.appStateDao().upsertWidgetConfig(new WidgetConfig(7, 1L)));
        assertEquals(new WidgetConfig(7, 1L), await(db.appStateDao().getWidgetConfig(7)));

        await(db.appStateDao().upsertWidgetConfig(new WidgetConfig(7, 2L)));
        assertEquals(new WidgetConfig(7, 2L), await(db.appStateDao().getWidgetConfig(7)));

        await(db.appStateDao().deleteWidgetConfig(7));
        assertNull(await(db.appStateDao().getWidgetConfig(7)));
    }

    @Test
    public void getWidgetConfigsOrderedById() {
        await(db.appStateDao().upsertWidgetConfig(new WidgetConfig(9, 1L)));
        await(db.appStateDao().upsertWidgetConfig(new WidgetConfig(3, 2L)));
        assertEquals(Arrays.asList(new WidgetConfig(3, 2L), new WidgetConfig(9, 1L)),
                await(db.appStateDao().getWidgetConfigs()));
    }

    // ---------- removeSourceReferences ----------

    @Test
    public void removeSourceReferencesClearsLaunchStateAndWidgets() {
        await(db.appStateDao().upsertLaunchState(new LaunchState(1L, Mode.DAILY, 3L)));
        await(db.appStateDao().upsertWidgetConfig(new WidgetConfig(1, 3L)));
        await(db.appStateDao().upsertWidgetConfig(new WidgetConfig(2, 4L)));
        await(db.appStateDao().upsertWidgetConfig(new WidgetConfig(5, 3L)));

        db.appStateDao().removeSourceReferences(3L);

        assertEquals(new LaunchState(1L, Mode.DAILY, null), await(db.appStateDao().getLaunchState()));
        assertEquals(Collections.singletonList(new WidgetConfig(2, 4L)), await(db.appStateDao().getWidgetConfigs()));
    }

    @Test
    public void removeSourceReferencesKeepsOtherSourceInLaunchState() {
        await(db.appStateDao().upsertLaunchState(new LaunchState(1L, Mode.PERSONAL, 4L)));
        db.appStateDao().removeSourceReferences(3L);
        assertEquals(new LaunchState(1L, Mode.PERSONAL, 4L), await(db.appStateDao().getLaunchState()));
    }
}
