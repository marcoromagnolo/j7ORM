package j7orm.type;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Marco Romagnolo
 */
public enum ValueType {
    BYTE(Byte.class),
    STRING(String.class),
    BOOLEAN3(Boolean.class),
    BOOLEAN(boolean.class),
    INTEGER(Integer.class),
    LONG(Long.class),
    SHORT(Short.class),
    FLOAT(Float.class),
    DOUBLE(Double.class),
    BIGDECIMAL(BigDecimal.class),
    ARRAY(Array.class),
    BYTEARRAY(Byte[].class),
    BLOB(Blob.class),
    CLOB(Clob.class),
    READER(Reader.class),
    STREAM(InputStream.class),
    TIME(Time.class),
    TIMESTAMP(Timestamp.class),
    SQLDATE(java.sql.Date.class),
    DATE(Date.class),
    CALENDAR(Calendar.class),
    URL(java.net.URL.class);

    private final Class<?> type;

    ValueType(Class<?> type) {
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }
}
