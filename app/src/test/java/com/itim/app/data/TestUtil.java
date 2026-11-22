package com.itim.app.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.TimeUnit;

/** עזרים לבדיקות ה-DAO: המתנה ל-ListenableFuture ולערך ראשון של LiveData. */
public final class TestUtil {

    private TestUtil() {
    }

    public static <T> T await(ListenableFuture<T> future) {
        try {
            return future.get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new AssertionError("future failed", e);
        }
    }

    /** דורש InstantTaskExecutorRule ו-executor ישיר לשאילתות. */
    public static <T> T getValue(LiveData<T> liveData) {
        final Object[] holder = new Object[1];
        final boolean[] set = {false};
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(T value) {
                holder[0] = value;
                set[0] = true;
            }
        };
        liveData.observeForever(observer);
        liveData.removeObserver(observer);
        if (!set[0]) {
            throw new AssertionError("LiveData did not emit a value");
        }
        @SuppressWarnings("unchecked")
        T value = (T) holder[0];
        return value;
    }
}
