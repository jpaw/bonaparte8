package de.jpaw.bonaparte8.util.functions;

import java.util.function.Function;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.core.MessageParserException;
import de.jpaw.bonaparte.core.StringBuilderParser;

public class StringRecord2Bonaparte implements Function <String,BonaPortable> {

    @Override
    public BonaPortable apply(String t) {
        if (t == null || t.length() == 0)
            return null;
        StringBuilderParser parser = new StringBuilderParser(t, 0, t.length());
        try {
            return parser.readRecord();
        } catch (MessageParserException e) {
            throw new RuntimeException(e);
        }
    }
}