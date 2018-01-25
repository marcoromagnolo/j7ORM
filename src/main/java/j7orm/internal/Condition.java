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

import j7orm.Entity;
import j7orm.exception.TableParseException;
import j7orm.type.LogicType;
import j7orm.type.OperatorType;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marco Romagnolo
 */
public class Condition {

    private static final Logger LOGGER = Logger.getLogger(Condition.class.getName());
    private String table;
    private String column;
    private Object value;
    private OperatorType op;
    private LogicType logic;

    public Condition(Class<? extends Entity> entityClass, String column, Object value) {
        this(entityClass, column, OperatorType.eq, value);
    }

    public Condition(Class<? extends Entity> entityClass, String column, OperatorType op, Object value) {
        this.value = value;
        this.op = op;
        try {
            EntityReflect<? extends Entity> reflect = EntityReflect.getInstance(entityClass);
            this.table = reflect.getTable();
            this.column = reflect.getColumnFromProperty(column);
        } catch (NoSuchFieldException | TableParseException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void setLogic(LogicType logic) {
        this.logic = logic;
    }

    public String getColumn() {
        return column;
    }

    public Object getValue() {
        return value;
    }

    public OperatorType getOp() {
        return op;
    }

    public LogicType getLogic() {
        return logic;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(table).append(".").append(column).append(op).append("?");
        return sb.toString();
    }
}
