package de.jpaw.bonaparte8.vertx3.codecs;

import java.util.HashMap;
import java.util.Map;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte8.vertx3.IMessageCoderFactory;
import de.jpaw.bonaparte8.vertx3.IMessageDecoder;
import de.jpaw.bonaparte8.vertx3.IMessageEncoder;


public class SingleThreadCachingMessageCoderFactory<D extends BonaPortable, E extends BonaPortable> implements IMessageCoderFactory<D, E, byte []> {
    private final Map<String,IMessageDecoder<D, byte []>> decoders = new HashMap<String,IMessageDecoder<D, byte []>>(10);
    private final Map<String,IMessageEncoder<E, byte []>> encoders = new HashMap<String,IMessageEncoder<E, byte []>>(10);
    
    private final Class<D> decoderClass;
    private final Class<E> encoderClass;
    
    public SingleThreadCachingMessageCoderFactory(Class<D> decoderClass, Class<E> encoderClass) {
        this.decoderClass = decoderClass;
        this.encoderClass = encoderClass;
    }
    
    // override to add additional methods
    protected IMessageEncoder<E, byte []> createNewEncoderInstance(String mimeType) {
        switch (mimeType) {
        case "application/bonaparte":
            return new BonaparteEncoder<E>();
        case "application/cbon":
            return new CompactBonaparteEncoder<E>();
        case "application/json":
            return new JsonEncoder<E>();
        }
        return null;
    }
    
    // override to add additional methods
    protected IMessageDecoder<D, byte []> createNewDecoderInstance(String mimeType) {
        switch (mimeType) {
        case "application/bonaparte":
            return new BonaparteDecoder<D>(decoderClass);
        case "application/cbon":
            return new CompactBonaparteDecoder<D>(decoderClass);
        case "application/json":
            return new JsonDecoder<D>();
        }
        return null;
    }
    
    @Override
    public final IMessageEncoder<E, byte []> getEncoderInstance(String mimeType) {
        IMessageEncoder<E, byte []> encoder = encoders.get(mimeType);
        if (encoder != null)
            return encoder;
        encoder = createNewEncoderInstance(mimeType);
        if (encoder != null)
            encoders.put(mimeType, encoder);
        return encoder;
    }

    @Override
    public IMessageDecoder<D, byte []> getDecoderInstance(String mimeType) {
        IMessageDecoder<D, byte []> decoder = decoders.get(mimeType);
        if (decoder != null)
            return decoder;
        decoder = createNewDecoderInstance(mimeType);
        if (decoder != null)
            decoders.put(mimeType, decoder);
        return decoder;
    }
}
