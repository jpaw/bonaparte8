package de.jpaw.bonaparte8.util.functions;

import java.util.function.Function;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.core.ByteArrayParser;
import de.jpaw.bonaparte.core.MessageParserException;

public class BytesRecord2Bonaparte implements Function <byte [], BonaPortable> {

    @Override
    public BonaPortable apply(byte [] t) {
        if (t == null || t.length == 0)
            return null;
        ByteArrayParser parser = new ByteArrayParser(t, 0, t.length);
        try {
            return parser.readRecord();
        } catch (MessageParserException e) {
            throw new RuntimeException(e);
        }
    }
}
