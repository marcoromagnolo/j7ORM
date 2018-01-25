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

import java.lang.reflect.Field;

/**
 * @author Marco Romagnolo
 */
public class ColumnData {

    private final boolean id;
    private final boolean version;
    private final String column;
    private final Field field;
    private final String table;
    private final String tableIndex;
    private final Class<?> type;
    private ValueType valueType;
    private Object value;

    public ColumnData(boolean id, boolean version, String column, String table, String tableIndex, Field field) {
        this.id = id;
        this.version = version;
        this.column = column;
        this.table = table;
        this.tableIndex = tableIndex;
        this.field = field;
        this.type = field.getType();
        for (ValueType valueType : ValueType.values()) {
            if (valueType.getType() == type) {
                this.valueType = valueType;
            }
        }
    }

    public boolean isId() {
        return id;
    }

    public boolean isVersion() {
        return version;
    }

    public String getColumn() {
        return column;
    }

    public String getTable() {
        return table;
    }

    public String getTableIndex() {
        return tableIndex;
    }

    public Field getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }

    public Class<?> getType() {
        return type;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
