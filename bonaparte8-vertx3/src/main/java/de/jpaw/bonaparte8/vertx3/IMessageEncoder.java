package de.jpaw.bonaparte8.vertx3;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.pojos.meta.ObjectReference;

public interface IMessageEncoder<O extends BonaPortable, T> {
    T encode(O obj, ObjectReference di);
}
