package com.itim.app.data;

import static org.junit.Assert.*;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;

public class ConvertersTest {

    @Test
    public void localDateToEpochDay() {
        assertEquals(Long.valueOf(0L), LocalDateConverter.fromLocalDate(LocalDate.of(1970, 1, 1)));
        assertEquals(Long.valueOf(LocalDate.of(2026, 10, 18).toEpochDay()), LocalDateConverter.fromLocalDate(LocalDate.of(2026, 10, 18)));
    }

    @Test
    public void epochDayToLocalDate() {
        assertEquals(LocalDate.of(1970, 1, 2), LocalDateConverter.toLocalDate(1L));
    }

    @Test
    public void localDateRoundTrip() {
        LocalDate date = LocalDate.of(2027, 3, 14);
        assertEquals(date, LocalDateConverter.toLocalDate(LocalDateConverter.fromLocalDate(date)));
    }

    @Test
    public void localDateOrderIsPreserved() {
        // השאילתות משוות תאריכים (BETWEEN, <) על הערך השמור, לכן הסדר חייב להישמר
        long earlier = LocalDateConverter.fromLocalDate(LocalDate.of(2026, 12, 31));
        long later = LocalDateConverter.fromLocalDate(LocalDate.of(2027, 1, 1));
        assertTrue(earlier < later);
    }

    @Test
    public void localDateNulls() {
        assertNull(LocalDateConverter.fromLocalDate(null));
        assertNull(LocalDateConverter.toLocalDate(null));
    }

    @Test
    public void instantToEpochMillis() {
        assertEquals(Long.valueOf(1234L), InstantConverter.fromInstant(Instant.ofEpochMilli(1234L)));
    }

    @Test
    public void epochMillisToInstant() {
        assertEquals(Instant.ofEpochMilli(987654321L), InstantConverter.toInstant(987654321L));
    }

    @Test
    public void instantRoundTripTruncatesToMillis() {
        Instant withNanos = Instant.ofEpochSecond(1_700_000_000L, 123_456_789L);
        assertEquals(Instant.ofEpochSecond(1_700_000_000L, 123_000_000L),
                InstantConverter.toInstant(InstantConverter.fromInstant(withNanos)));
    }

    @Test
    public void instantNulls() {
        assertNull(InstantConverter.fromInstant(null));
        assertNull(InstantConverter.toInstant(null));
    }
}
