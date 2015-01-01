package de.jpaw.bonaparte8.util.functions;

import java.util.function.Function;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.core.CompactByteArrayParser;
import de.jpaw.bonaparte.core.MessageParserException;

public class CompactRecord2Bonaparte implements Function <byte [],BonaPortable> {

    @Override
    public BonaPortable apply(byte [] t) {
        if (t == null || t.length == 0)
            return null;
        CompactByteArrayParser parser = new CompactByteArrayParser(t, 0, t.length);
        try {
            return parser.readRecord();
        } catch (MessageParserException e) {
            throw new RuntimeException(e);
        }
    }
}