package com.itim.app.data.personal;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/** מסד אישי ריק בזיכרון (בלי ה-Callback של getInstance, שתלוי במסד התוכן). */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 35)
public abstract class PersonalDatabaseTestBase {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    protected PersonalDatabase db;

    @Before
    public final void createDb() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), PersonalDatabase.class)
                .allowMainThreadQueries()
                .setQueryExecutor(Runnable::run)
                .setTransactionExecutor(Runnable::run)
                .build();
    }

    @After
    public final void closeDb() {
        db.close();
    }
}
