package de.jpaw.bonaparte8.vertx3.codecs;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.core.CompactByteArrayParser;
import de.jpaw.bonaparte.core.MessageParserException;
import de.jpaw.bonaparte.pojos.meta.ObjectReference;
import de.jpaw.bonaparte8.vertx3.IMessageDecoder;

public class CompactBonaparteDecoder<O extends BonaPortable> implements IMessageDecoder<O, byte []> {
    
    @Override
    public O decode(byte [] data, ObjectReference di) throws MessageParserException {
        final CompactByteArrayParser cbap = new CompactByteArrayParser(data, 0, -1);
        return (O) cbap.readObject(di, BonaPortable.class);
    }
}
