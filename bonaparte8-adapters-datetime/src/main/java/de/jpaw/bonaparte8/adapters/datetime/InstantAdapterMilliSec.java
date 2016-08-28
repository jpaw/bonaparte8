package de.jpaw.bonaparte8.adapters.datetime;

import org.joda.time.Instant;

/** Converts between Java 8 Instant and Joda Instant, truncating to milliseconds. */
public class InstantAdapterMilliSec {

    public static Instant unmarshal(java.time.Instant obj) {
        return new Instant(obj.toEpochMilli());
    }

    public static java.time.Instant marshal(Instant data) {
        return data == null ? null : java.time.Instant.ofEpochMilli(data.getMillis());
    }
}
