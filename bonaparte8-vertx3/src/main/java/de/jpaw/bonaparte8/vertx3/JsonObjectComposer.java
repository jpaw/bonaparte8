package de.jpaw.bonaparte8.vertx3;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import de.jpaw.bonaparte.core.AbstractMessageComposer;
import de.jpaw.bonaparte.core.BonaCustom;
import de.jpaw.bonaparte.core.StaticMeta;
import de.jpaw.bonaparte.enums.BonaNonTokenizableEnum;
import de.jpaw.bonaparte.enums.BonaTokenizableEnum;
import de.jpaw.bonaparte.pojos.meta.AlphanumericElementaryDataItem;
import de.jpaw.bonaparte.pojos.meta.BasicNumericElementaryDataItem;
import de.jpaw.bonaparte.pojos.meta.BinaryElementaryDataItem;
import de.jpaw.bonaparte.pojos.meta.EnumDataItem;
import de.jpaw.bonaparte.pojos.meta.FieldDefinition;
import de.jpaw.bonaparte.pojos.meta.MiscElementaryDataItem;
import de.jpaw.bonaparte.pojos.meta.NumericElementaryDataItem;
import de.jpaw.bonaparte.pojos.meta.ObjectReference;
import de.jpaw.bonaparte.pojos.meta.TemporalElementaryDataItem;
import de.jpaw.bonaparte.pojos.meta.XEnumDataItem;
import de.jpaw.enums.XEnum;
import de.jpaw.util.ByteArray;

/** Composer which serializes all data into a vertx JsonObject */
public class JsonObjectComposer extends AbstractMessageComposer<RuntimeException> {
    protected static final DateTimeFormatter LOCAL_DATE_ISO = ISODateTimeFormat.basicDate();
    protected static final DateTimeFormatter LOCAL_DATETIME_ISO = ISODateTimeFormat.basicDateTime();
    protected static final DateTimeFormatter LOCAL_TIME_ISO = ISODateTimeFormat.basicTime();

    protected JsonObject obj = null;
    protected JsonArray arr = null;
    protected boolean inArray = false;
    protected boolean writeNulls = false;
    protected String rememberedArrayFieldName = null;


    // static converter method for convenience
    public static JsonObject toJsonObject(BonaCustom o, boolean writeNulls) {
        JsonObjectComposer composer = new JsonObjectComposer(writeNulls);
        composer.writeRecord(o);
        return composer.getObject();
    }
    public static JsonObject toJsonObject(BonaCustom o) {
        return toJsonObject(o, false);
    }

    // retrieve the converted object
    public JsonObject getObject() {
        return obj;
    }

    public JsonObjectComposer() {
    }

    public JsonObjectComposer(boolean writeNulls) {
        this.writeNulls = writeNulls;
    }


    @Override
    public void writeNull(FieldDefinition di) { // nulls are not written, unless we are in an array
        if (inArray) {
            arr.addNull();
        } else if (writeNulls) {
            obj.putNull(di.getName());
        }
    }

    @Override
    public void writeNullCollection(FieldDefinition di) {
    }

    @Override
    public void startTransmission() {
    }

    @Override
    public void startRecord() {
        // reset to consistent state for sanity
        obj = null;
        arr = null;
        inArray = false;
    }

    @Override
    public void startObject(ObjectReference di, BonaCustom o) {
        obj = new JsonObject();
        obj.put("_PQON", o.ret$PQON());  // insert the actual object type
    }

    @Override
    public void terminateObject(ObjectReference di, BonaCustom o) {
    }

    @Override
    public void startArray(FieldDefinition di, int currentMembers, int sizeOfElement) {
        if (inArray)
            throw new IllegalArgumentException("Nested arrays are not supported");
        inArray = true;
        arr = new JsonArray();
        rememberedArrayFieldName = di.getName();
    }

    @Override
    public void startMap(FieldDefinition di, int currentMembers) {
        throw new IllegalArgumentException("Maps are not supported for Json conversion.");
    }

    @Override
    public void writeSuperclassSeparator() {
    }

    @Override
    public void terminateMap() {
    }

    @Override
    public void terminateArray() {
        if (!inArray) {
            throw new IllegalArgumentException("Cannot terminate array, as non is open");
        }
        obj.put(rememberedArrayFieldName, arr);
        inArray = false;
        arr = null;
    }

    @Override
    public void terminateRecord() {
    }

    @Override
    public void terminateTransmission() {
    }

    @Override
    public void writeRecord(BonaCustom o) {
        startRecord();
        // addField(StaticMeta.OUTER_BONAPORTABLE, o);
        // start a new object
        startObject(StaticMeta.OUTER_BONAPORTABLE, o);
        o.serializeSub(this);
        terminateObject(StaticMeta.OUTER_BONAPORTABLE, o);
        terminateRecord();
    }

    @Override
    public void addField(ObjectReference di, BonaCustom o) {
        if (inArray) {
            if (o != null)
                arr.add(toJsonObject(o, writeNulls));
            else
                arr.addNull();
        } else {
            if (o != null)
                obj.put(di.getName(), toJsonObject(o, writeNulls));
            else if (writeNulls)
                writeNull(di);
        }
    }

    @Override
    public void addField(MiscElementaryDataItem di, boolean b) {
        if (inArray)
            arr.add(b);
        else
            obj.put(di.getName(), b);
    }

    @Override
    public void addField(MiscElementaryDataItem di, char c) {
        if (inArray)
            arr.add(String.valueOf(c));
        else
            obj.put(di.getName(), String.valueOf(c));
    }

    @Override
    public void addField(BasicNumericElementaryDataItem di, double d) {
        if (inArray)
            arr.add(d);
        else
            obj.put(di.getName(), d);
    }

    @Override
    public void addField(BasicNumericElementaryDataItem di, float f) {
        if (inArray)
            arr.add(f);
        else
            obj.put(di.getName(), f);
    }

    @Override
    public void addField(BasicNumericElementaryDataItem di, byte n) {
        if (inArray)
            arr.add(n);
        else
            obj.put(di.getName(), n);
    }

    @Override
    public void addField(BasicNumericElementaryDataItem di, short n) {
        if (inArray)
            arr.add(n);
        else
            obj.put(di.getName(), n);
    }

    @Override
    public void addField(BasicNumericElementaryDataItem di, int n) {
        if (inArray)
            arr.add(n);
        else
            obj.put(di.getName(), n);
    }

    @Override
    public void addField(BasicNumericElementaryDataItem di, long n) {
        if (inArray)
            arr.add(n);
        else
            obj.put(di.getName(), n);
    }

    @Override
    public void addField(AlphanumericElementaryDataItem di, String s) {
        if (inArray) {
            if (s != null)
                arr.add(s);
            else
                arr.addNull();
        } else {
            if (s != null)
                obj.put(di.getName(), s);
            else if (writeNulls)
                writeNull(di);
        }
    }

    @Override
    public void addField(MiscElementaryDataItem di, UUID n) {
        if (inArray) {
            if (n != null)
                arr.add(n.toString());
            else
                arr.addNull();
        } else {
            if (n != null)
                obj.put(di.getName(), n.toString());
            else if (writeNulls)
                writeNull(di);
        }
    }

    @Override
    public void addField(BinaryElementaryDataItem di, ByteArray b) {
        if (inArray) {
            if (b != null)
                arr.add(b.getBytes());
            else
                arr.addNull();
        } else {
            if (b != null)
                obj.put(di.getName(), b.getBytes());
            else if (writeNulls)
                writeNull(di);
        }
    }

    @Override
    public void addField(BinaryElementaryDataItem di, byte[] b) {
        if (inArray) {
            if (b != null)
                arr.add(b);
            else
                arr.addNull();
        } else {
            if (b != null)
                obj.put(di.getName(), b);
            else if (writeNulls)
                writeNull(di);
        }
    }

    @Override
    public void addField(BasicNumericElementaryDataItem di, BigInteger n) {
        if (inArray) {
            if (n != null)
                arr.add(n.toString());
            else
                arr.addNull();
        } else {
            if (n != null)
                obj.put(di.getName(), n.toString());
            else if (writeNulls)
                writeNull(di);
        }
    }

    @Override
    public void addField(NumericElementaryDataItem di, BigDecimal n) {
        if (inArray) {
            if (n != null)
                arr.add(n.toString());
            else
                arr.addNull();
        } else {
            if (n != null)
                obj.put(di.getName(), n.toString());
            else if (writeNulls)
                writeNull(di);
        }
    }

    @Override
    public void addField(TemporalElementaryDataItem di, LocalDate t) {
        if (inArray) {
            if (t != null)
                arr.add(LOCAL_DATE_ISO.print(t));
            else
                arr.addNull();
        } else {
            if (t != null)
                obj.put(di.getName(), LOCAL_DATE_ISO.print(t));
            else if (writeNulls)
                writeNull(di);
        }
    }

    @Override
    public void addField(TemporalElementaryDataItem di, LocalDateTime t) {
        if (inArray) {
            if (t != null)
                arr.add(LOCAL_DATETIME_ISO.print(t));
            else
                arr.addNull();
        } else {
            if (t != null)
                obj.put(di.getName(), LOCAL_DATETIME_ISO.print(t));
            else if (writeNulls)
                writeNull(di);
        }
    }

    @Override
    public void addField(TemporalElementaryDataItem di, LocalTime t) {
        if (inArray) {
            if (t != null)
                arr.add(LOCAL_TIME_ISO.print(t));
            else
                arr.addNull();
        } else {
            if (t != null)
                obj.put(di.getName(), LOCAL_TIME_ISO.print(t));
            else if (writeNulls)
                writeNull(di);
        }
    }

    @Override
    public void addField(TemporalElementaryDataItem di, Instant t) {
        if (inArray) {
            if (t != null)
                arr.add(LOCAL_DATETIME_ISO.print(t));
            else
                arr.addNull();
        } else {
            if (t != null)
                obj.put(di.getName(), LOCAL_DATETIME_ISO.print(t));
            else if (writeNulls)
                writeNull(di);
        }
    }

    @Override
    public void addEnum(EnumDataItem di, BasicNumericElementaryDataItem ord, BonaNonTokenizableEnum n) {
        if (inArray) {
            if (n != null)
                arr.add(n.ordinal());
            else
                arr.addNull();
        } else {
            if (n != null)
                obj.put(di.getName(), n.ordinal());
            else if (writeNulls)
                writeNull(di);
        }
    }

    @Override
    public void addEnum(EnumDataItem di, AlphanumericElementaryDataItem token, BonaTokenizableEnum n) {
        if (inArray) {
            if (n != null)
                arr.add(n.getToken());
            else
                arr.addNull();
        } else {
            if (n != null)
                obj.put(di.getName(), n.getToken());
            else if (writeNulls)
                writeNull(di);
        }
    }

    @Override
    public void addEnum(XEnumDataItem di, AlphanumericElementaryDataItem token, XEnum<?> n) {
        if (inArray) {
            if (n != null)
                arr.add(n.getToken());
            else
                arr.addNull();
        } else {
            if (n != null)
                obj.put(di.getName(), n.getToken());
            else if (writeNulls)
                writeNull(di);
        }
    }

    @Override
    public boolean addExternal(ObjectReference di, Object obj) {
        return false;       // perform conversion by default
    }
}
