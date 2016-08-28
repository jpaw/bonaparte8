package de.jpaw.bonaparte8.adapters.datetime.test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.testng.Assert;
import org.testng.annotations.Test;

import de.jpaw.bonaparte.util.DayTime;
import de.jpaw.bonaparte8.adapters.datetime.InstantAdapterMilliSec;
import de.jpaw.bonaparte8.adapters.datetime.InstantAdapterSecond;
import de.jpaw.bonaparte8.adapters.datetime.LocalDateAdapter;
import de.jpaw.bonaparte8.adapters.datetime.LocalDateTimeAdapterMilliSec;
import de.jpaw.bonaparte8.adapters.datetime.LocalDateTimeAdapterSecond;
import de.jpaw.bonaparte8.adapters.datetime.LocalTimeAdapterMilliSec;
import de.jpaw.bonaparte8.adapters.datetime.LocalTimeAdapterSecond;

public class ConvertersTest {
    // since the precision of Joda is one millisecond, we can use now() to create random instances and expect that their value
    // is identical after a roundtrip to Java 8 date / time and back. Please note that the opposite will not be true, as sub-millisecond
    // portions will be cut off.

    @Test
    public void testJava8ConversionInstant() throws Exception {

        Instant currentInstant = Instant.now();
        Instant newInstant = InstantAdapterMilliSec.marshal(InstantAdapterMilliSec.unmarshal(currentInstant));

        Assert.assertEquals(currentInstant.equals(newInstant), true);

        // now do the same with possible truncation
        newInstant = InstantAdapterSecond.marshal(InstantAdapterSecond.unmarshal(currentInstant));

        // evaluation of the result
        long originalMillis = DayTime.millisOfEpoch(currentInstant);
        long truncatedMillis = DayTime.millisOfEpoch(newInstant);

        Assert.assertEquals(truncatedMillis % 1000, 0);
        Assert.assertEquals(truncatedMillis <= originalMillis, true);
        Assert.assertEquals((truncatedMillis + 999) >= originalMillis, true);
    }

    @Test
    public void testJava8ConversionLocalDateTime() throws Exception {

        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        LocalDateTime newLocalDateTime = LocalDateTimeAdapterMilliSec.marshal(LocalDateTimeAdapterMilliSec.unmarshal(currentLocalDateTime));

        Assert.assertEquals(currentLocalDateTime.equals(newLocalDateTime), true);

        // now do the same with possible truncation
        newLocalDateTime = LocalDateTimeAdapterSecond.marshal(LocalDateTimeAdapterSecond.unmarshal(currentLocalDateTime));

        // evaluation of the result
        long originalMillis = DayTime.timeAsInt(currentLocalDateTime) + 1000_000_000L * DayTime.dayAsInt(currentLocalDateTime);
        long truncatedMillis = DayTime.timeAsInt(newLocalDateTime) + 1000_000_000L * DayTime.dayAsInt(newLocalDateTime);

        Assert.assertEquals(truncatedMillis % 1000, 0);
        Assert.assertEquals(truncatedMillis <= originalMillis, true);
        Assert.assertEquals((truncatedMillis + 999) >= originalMillis, true);
    }

    @Test
    public void testJava8ConversionLocalTime() throws Exception {

        LocalTime currentLocalTime = LocalTime.now();
        LocalTime newLocalTime = LocalTimeAdapterMilliSec.marshal(LocalTimeAdapterMilliSec.unmarshal(currentLocalTime));

        Assert.assertEquals(currentLocalTime.equals(newLocalTime), true);

        // now do the same with possible truncation
        newLocalTime = LocalTimeAdapterSecond.marshal(LocalTimeAdapterSecond.unmarshal(currentLocalTime));

        // evaluation of the result
        long originalMillis = currentLocalTime.toNanoOfDay() / 1000000L;
        long truncatedMillis = newLocalTime.toNanoOfDay() / 1000000L;

        Assert.assertEquals(truncatedMillis % 1000, 0);
        Assert.assertEquals(truncatedMillis <= originalMillis, true);
        Assert.assertEquals((truncatedMillis + 999) >= originalMillis, true);
    }

    @Test
    public void testJava8ConversionDay() throws Exception {

        LocalDate someDay = LocalDate.of(1958, 7, 30);
        LocalDate newDay = LocalDateAdapter.marshal(LocalDateAdapter.unmarshal(someDay));

        Assert.assertEquals(someDay.equals(newDay), true);
    }
}
