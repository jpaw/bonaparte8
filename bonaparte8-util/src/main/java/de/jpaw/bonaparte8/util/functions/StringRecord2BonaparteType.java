package de.jpaw.bonaparte8.util.functions;

import java.util.function.Function;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.core.MessageParserException;
import de.jpaw.bonaparte.core.StringBuilderParser;

public class StringRecord2BonaparteType<T extends BonaPortable> implements Function <String,T> {
    private final Class<T> classToExpect;

    public StringRecord2BonaparteType(Class<T> classToExpect) {
        this.classToExpect = classToExpect;
    }

    @Override
    public T apply(String t) {
        if (t == null || t.length() == 0)
            return null;
        StringBuilderParser parser = new StringBuilderParser(t, 0, t.length());
        try {
            return classToExpect.cast(parser.readRecord());
        } catch (MessageParserException e) {
            throw new RuntimeException(e);
        }
    }

}
