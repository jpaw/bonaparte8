package de.jpaw.bonaparte8.vertx3.codecs;

import java.util.HashMap;
import java.util.Map;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte8.vertx3.IMessageCoderFactory;
import de.jpaw.bonaparte8.vertx3.IMessageDecoder;
import de.jpaw.bonaparte8.vertx3.IMessageEncoder;


public class SingleThreadCachingMessageCoderFactory<O extends BonaPortable> implements IMessageCoderFactory<O, byte []> {
    private final Map<String,IMessageDecoder<O,byte []>> decoders = new HashMap<String,IMessageDecoder<O,byte []>>(10);
    private final Map<String,IMessageEncoder<O,byte []>> encoders = new HashMap<String,IMessageEncoder<O,byte []>>(10);
    
    // override to add additional methods
    protected IMessageEncoder<O, byte []> createNewEncoderInstance(String mimeType) {
        switch (mimeType) {
        case "application/bonaparte":
            return new BonaparteEncoder<O>();
        case "application/cbon":
            return new CompactBonaparteEncoder<O>();
        case "application/json":
            return new JsonEncoder<O>();
        }
        return null;
    }
    
    // override to add additional methods
    protected IMessageDecoder<O, byte []> createNewDecoderInstance(String mimeType) {
        switch (mimeType) {
        case "application/bonaparte":
            return new BonaparteDecoder<O>();
        case "application/cbon":
            return new CompactBonaparteDecoder<O>();
        case "application/json":
            return new JsonDecoder<O>();
        }
        return null;
    }
    
    @Override
    public final IMessageEncoder<O, byte []> getEncoderInstance(String mimeType) {
        IMessageEncoder<O,byte []> encoder = encoders.get(mimeType);
        if (encoder != null)
            return encoder;
        encoder = createNewEncoderInstance(mimeType);
        if (encoder != null)
            encoders.put(mimeType, encoder);
        return encoder;
    }

    @Override
    public IMessageDecoder<O, byte []> getDecoderInstance(String mimeType) {
        IMessageDecoder<O,byte []> decoder = decoders.get(mimeType);
        if (decoder != null)
            return decoder;
        decoder = createNewDecoderInstance(mimeType);
        if (decoder != null)
            decoders.put(mimeType, decoder);
        return decoder;
    }

}
