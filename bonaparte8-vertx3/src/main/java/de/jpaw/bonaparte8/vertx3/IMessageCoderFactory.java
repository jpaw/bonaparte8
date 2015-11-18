package de.jpaw.bonaparte8.vertx3;

import de.jpaw.bonaparte.core.BonaPortable;

// 
public interface IMessageCoderFactory<O extends BonaPortable, T> {
    IMessageEncoder<O, T> getEncoderInstance(String mimeType);
    IMessageDecoder<O, T> getDecoderInstance(String mimeType);
}
