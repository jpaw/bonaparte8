package de.jpaw.bonaparte8.vertx3.codecs;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.core.CompactByteArrayComposer;
import de.jpaw.bonaparte.pojos.meta.ObjectReference;
import de.jpaw.bonaparte8.vertx3.IMessageEncoder;

public class CompactBonaparteEncoder<O extends BonaPortable> implements IMessageEncoder<O, byte []> {
    private final CompactByteArrayComposer bac = new CompactByteArrayComposer();
    
    @Override
    public byte[] encode(O obj, ObjectReference di) {
        bac.reset();
        bac.addField(di, obj);
        return bac.getBytes();
    }
}
