package de.jpaw.bonaparte8.vertx3.codecs;

import java.nio.charset.StandardCharsets;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.core.MapParser;
import de.jpaw.bonaparte.pojos.meta.ObjectReference;
import de.jpaw.bonaparte8.vertx3.IMessageDecoder;
import de.jpaw.json.JsonParser;
import de.jpaw.util.ApplicationException;

public class JsonDecoder<O extends BonaPortable> implements IMessageDecoder<O, byte []> {
    
    @Override
    public O decode(byte [] data, ObjectReference di) throws ApplicationException {
        final JsonParser jp = new JsonParser(new String(data, StandardCharsets.UTF_8), false);
        return (O) MapParser.asBonaPortable(jp.parseObject(), di);
    }
}
