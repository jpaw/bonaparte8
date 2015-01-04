package de.jpaw.bonaparte8.adapters.datetime.test;

import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.testng.Assert;
import org.testng.annotations.Test;

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
        
        Instant currentInstant = new Instant();
        Instant newInstant = InstantAdapterMilliSec.marshal(InstantAdapterMilliSec.unmarshal(currentInstant));
        
        Assert.assertEquals(currentInstant.equals(newInstant), true);

        // now do the same with possible truncation
        newInstant = InstantAdapterSecond.marshal(InstantAdapterSecond.unmarshal(currentInstant));
        
        // evaluation of the result
        long originalMillis = currentInstant.getMillis();
        long truncatedMillis = newInstant.getMillis();
        
        Assert.assertEquals(truncatedMillis % 1000, 0);
        Assert.assertEquals(truncatedMillis <= originalMillis, true);
        Assert.assertEquals((truncatedMillis + 999) >= originalMillis, true);
    }
    
    @Test
    public void testJava8ConversionLocalDateTime() throws Exception {
        
        LocalDateTime currentLocalDateTime = new LocalDateTime();
        LocalDateTime newLocalDateTime = LocalDateTimeAdapterMilliSec.marshal(LocalDateTimeAdapterMilliSec.unmarshal(currentLocalDateTime));
        
        Assert.assertEquals(currentLocalDateTime.equals(newLocalDateTime), true);

        // now do the same with possible truncation
        newLocalDateTime = LocalDateTimeAdapterSecond.marshal(LocalDateTimeAdapterSecond.unmarshal(currentLocalDateTime));
        
        // evaluation of the result
        long originalMillis = currentLocalDateTime.getMillisOfDay();
        long truncatedMillis = newLocalDateTime.getMillisOfDay();
        
        Assert.assertEquals(truncatedMillis % 1000, 0);
        Assert.assertEquals(truncatedMillis <= originalMillis, true);
        Assert.assertEquals((truncatedMillis + 999) >= originalMillis, true);
    }
    
    @Test
    public void testJava8ConversionLocalTime() throws Exception {
        
        LocalTime currentLocalTime = new LocalTime();
        LocalTime newLocalTime = LocalTimeAdapterMilliSec.marshal(LocalTimeAdapterMilliSec.unmarshal(currentLocalTime));
        
        Assert.assertEquals(currentLocalTime.equals(newLocalTime), true);

        // now do the same with possible truncation
        newLocalTime = LocalTimeAdapterSecond.marshal(LocalTimeAdapterSecond.unmarshal(currentLocalTime));
        
        // evaluation of the result
        long originalMillis = currentLocalTime.getMillisOfDay();
        long truncatedMillis = newLocalTime.getMillisOfDay();
        
        Assert.assertEquals(truncatedMillis % 1000, 0);
        Assert.assertEquals(truncatedMillis <= originalMillis, true);
        Assert.assertEquals((truncatedMillis + 999) >= originalMillis, true);
    }
    
    @Test
    public void testJava8ConversionDay() throws Exception {
        
        LocalDate someDay = new LocalDate(1958, 7, 30);
        LocalDate newDay = LocalDateAdapter.marshal(LocalDateAdapter.unmarshal(someDay));
        
        Assert.assertEquals(someDay.equals(newDay), true);
    }
}
