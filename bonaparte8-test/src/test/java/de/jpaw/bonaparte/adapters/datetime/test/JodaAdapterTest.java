package de.jpaw.bonaparte.adapters.datetime.test;

import org.joda.time.Instant;
import org.joda.time.LocalDate;

import org.testng.annotations.Test;

import de.jpaw.bonaparte.pojos.adapters.datetime.test.UsingJodaTimeTypes;
import de.jpaw.bonaparte.testrunner.MultiTestRunner;
import de.jpaw.util.StringSerializer;

public class JodaAdapterTest {

    @Test
    public void testJodaTimeConversion() throws Exception {
        long millis = 98794375943L;
        // Instant.now().truncatedTo(Sec.)
        UsingJodaTimeTypes myData = new UsingJodaTimeTypes(new Instant(millis), new LocalDate(2010, 12, 31));

        String expectedResult = StringSerializer.fromString(
                "\\R\\N\\Sadapters.datetime.test.UsingJodaTimeTypes\\F\\N98794375.943\\F20101231\\F\\O\\J").toString();
        MultiTestRunner.serDeserMulti(myData, expectedResult);
    }
}
