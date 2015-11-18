package de.jpaw.bonaparte8.vertx3.codecs;

import java.nio.charset.StandardCharsets;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.core.BonaparteJsonEscaper;
import de.jpaw.bonaparte.pojos.meta.ObjectReference;
import de.jpaw.bonaparte8.vertx3.IMessageEncoder;

public class JsonEncoder<O extends BonaPortable> implements IMessageEncoder<O, byte []> {
    
    @Override
    public byte[] encode(O obj, ObjectReference di) {
        return BonaparteJsonEscaper.asJson(obj).getBytes(StandardCharsets.UTF_8);
    }
}
