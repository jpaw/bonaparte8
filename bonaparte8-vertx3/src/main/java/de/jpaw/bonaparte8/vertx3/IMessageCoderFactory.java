package de.jpaw.bonaparte8.vertx3;

import de.jpaw.bonaparte.core.BonaPortable;

// 
public interface IMessageCoderFactory<D extends BonaPortable, E extends BonaPortable, T> {
    IMessageEncoder<E, T> getEncoderInstance(String mimeType);
    IMessageDecoder<D, T> getDecoderInstance(String mimeType);
}
