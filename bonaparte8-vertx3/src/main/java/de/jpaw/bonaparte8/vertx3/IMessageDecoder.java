package de.jpaw.bonaparte8.vertx3;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.pojos.meta.ObjectReference;
import de.jpaw.util.ApplicationException;

public interface IMessageDecoder<O extends BonaPortable, T> {
    O decode(T data, ObjectReference di) throws ApplicationException;
}
