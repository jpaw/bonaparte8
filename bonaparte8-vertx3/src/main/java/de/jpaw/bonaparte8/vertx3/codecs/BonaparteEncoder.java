package de.jpaw.bonaparte8.vertx3.codecs;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.core.ByteArrayComposer;
import de.jpaw.bonaparte.pojos.meta.ObjectReference;
import de.jpaw.bonaparte8.vertx3.IMessageEncoder;

public class BonaparteEncoder<O extends BonaPortable> implements IMessageEncoder<O, byte []> {
    private final ByteArrayComposer bac = new ByteArrayComposer();
    
    @Override
    public byte[] encode(O obj, ObjectReference di) {
        bac.reset();
        bac.addField(di, obj);
        return bac.getBytes();
    }
}
