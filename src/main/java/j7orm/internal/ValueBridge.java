/**
 * Copyright 2016 Advant I/O
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package j7orm.internal;

import j7orm.type.ValueType;

import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;

/**
 * @author Marco Romagnolo
 */
public class ValueBridge {

    public static void setStatement(PreparedStatement pstmt, int i, ValueType type, Object value) throws SQLException {
        switch (type) {

            case BYTE:
                pstmt.setByte(i, (Byte) value);
                break;
            case STRING:
                pstmt.setString(i, (String) value);
                break;
            case BOOLEAN3:
            case BOOLEAN:
                pstmt.setBoolean(i, (Boolean) value);
                break;
            case INTEGER:
                pstmt.setInt(i, (Integer) value);
                break;
            case LONG:
                pstmt.setLong(i, (Long) value);
                break;
            case SHORT:
                pstmt.setShort(i, (Short) value);
                break;
            case FLOAT:
                pstmt.setFloat(i, (Float) value);
                break;
            case DOUBLE:
                pstmt.setDouble(i, (Double) value);
                break;
            case BIGDECIMAL:
                pstmt.setBigDecimal(i, (BigDecimal) value);
                break;
            case ARRAY:
                pstmt.setArray(i, (Array) value);
                break;
            case BYTEARRAY:
                pstmt.setBytes(i, (byte[]) value);
                break;
            case BLOB:
                pstmt.setBlob(i, (Blob) value);
                break;
            case CLOB:
                pstmt.setClob(i, (Clob) value);
                break;
            case READER:
                pstmt.setCharacterStream(i, (Reader) value);
                break;
            case STREAM:
                pstmt.setBinaryStream(i, (InputStream) value);
                break;
            case TIME:
                pstmt.setTime(i, (Time) value);
                break;
            case TIMESTAMP:
                pstmt.setTimestamp(i, (Timestamp) value);
                break;
            case SQLDATE:
                pstmt.setDate(i, (java.sql.Date) value);
                break;
            case DATE:
                java.sql.Date date = (value == null) ? null : new Date(((java.util.Date) value).getTime());
                pstmt.setDate(i, date);
                break;
            case CALENDAR:
                java.sql.Date calDate = (value == null) ? null : new Date(((java.util.Calendar) value).getTime().getTime());
                pstmt.setDate(i, calDate);
                break;
            case URL:
                pstmt.setURL(i, (URL) value);
                break;
        }
    }

    public static void setField(Object entity, Field field, ValueType type, String columnName, ResultSet resultSet) throws IllegalAccessException, SQLException {
        switch (type) {

            case BYTE:
                field.setByte(entity, resultSet.getByte(columnName));
                break;
            case STRING:
                field.set(entity, resultSet.getString(columnName));
                break;
            case BOOLEAN3:
                field.set(entity, resultSet.getObject(columnName));
                break;
            case BOOLEAN:
                field.setBoolean(entity, resultSet.getBoolean(columnName));
                break;
            case INTEGER:
                int intVal = resultSet.getInt(columnName);
                field.set(entity, intVal == 0 ? null : intVal);
                break;
            case LONG:
                long longVal = resultSet.getLong(columnName);
                field.set(entity, longVal == 0L ? null : longVal);
                break;
            case SHORT:
                short shortVal = resultSet.getShort(columnName);
                field.set(entity, shortVal == 0 ? null : shortVal);
                break;
            case FLOAT:
                float floatVal = resultSet.getFloat(columnName);
                field.set(entity, floatVal == 0 ? null : floatVal);
                break;
            case DOUBLE:
                double doubleVal = resultSet.getDouble(columnName);
                field.set(entity, doubleVal == 0 ? null : doubleVal);
                break;
            case BIGDECIMAL:
                field.set(entity, resultSet.getBigDecimal(columnName));
                break;
            case ARRAY:
                field.set(entity, resultSet.getArray(columnName));
                break;
            case BYTEARRAY:
                field.set(entity, resultSet.getBytes(columnName));
                break;
            case BLOB:
                field.set(entity, resultSet.getBlob(columnName));
                break;
            case CLOB:
                field.set(entity, resultSet.getClob(columnName));
                break;
            case READER:
                field.set(entity, resultSet.getCharacterStream(columnName));
                break;
            case STREAM:
                field.set(entity, resultSet.getBinaryStream(columnName));
                break;
            case TIME:
                field.set(entity, resultSet.getTime(columnName));
                break;
            case TIMESTAMP:
                field.set(entity, resultSet.getTimestamp(columnName));
                break;
            case SQLDATE:
                field.set(entity, resultSet.getDate(columnName));
                break;
            case DATE:
                field.set(entity, resultSet.getDate(columnName));
                break;
            case CALENDAR:
                java.util.Date calDate = resultSet.getDate(columnName);
                Calendar calVal = Calendar.getInstance();
                calVal.setTime(calDate);
                field.set(entity, calVal);
                break;
            case URL:
                field.set(entity, resultSet.getURL(columnName));
                break;
        }
    }

}
