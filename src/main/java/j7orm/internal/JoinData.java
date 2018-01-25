/**
 * Copyright 2016 Marco Romagnolo
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

import java.util.Objects;

/**
 * @author Marco Romagnolo
 */
public class JoinData {
    private final String table;
    private final String column;
    private final String joinTable;
    private final String joinColumn;
    private Object value;

    public JoinData(String table, String column, String joinTable, String joinColumn) {
        this.table = table;
        this.column = column;
        this.joinTable = joinTable;
        this.joinColumn = joinColumn;
    }

    public String getTable() {
        return table;
    }

    public String getColumn() {
        return column;
    }

    public String getJoinTable() {
        return joinTable;
    }

    public String getJoinColumn() {
        return joinColumn;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JoinData joinData = (JoinData) o;
        return Objects.equals(joinTable, joinData.joinTable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(joinTable);
    }
}
