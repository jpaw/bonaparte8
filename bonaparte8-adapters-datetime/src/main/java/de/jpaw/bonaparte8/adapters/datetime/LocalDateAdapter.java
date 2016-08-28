package de.jpaw.bonaparte8.adapters.datetime;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

public class LocalDateAdapter {

    public static LocalDate unmarshal(java.time.LocalDate obj) {
        return new LocalDate(obj.toEpochDay() * 86400_000L, DateTimeZone.UTC);
    }

    public static java.time.LocalDate marshal(LocalDate data) {
        return data == null ? null : java.time.LocalDate.of(data.getYear(), data.getMonthOfYear(), data.getDayOfMonth());
    }
}
