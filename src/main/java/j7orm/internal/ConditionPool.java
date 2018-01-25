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

import j7orm.type.LogicType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Marco Romagnolo
 */
public class ConditionPool {

    private List<Condition> conditions = new ArrayList<>();
    private ConditionPool pool;
    private LogicType logic = LogicType.AND;

    public ConditionPool(Condition condition) {
        Collections.addAll(this.conditions, condition);
    }

    public LogicType getLogic() {
        return logic;
    }

    public ConditionPool or(Condition condition) {
        condition.setLogic(LogicType.OR);
        Collections.addAll(this.conditions, condition);
        return this;
    }

    public ConditionPool and(Condition condition) {
        condition.setLogic(LogicType.AND);
        Collections.addAll(this.conditions, condition);
        return this;
    }

    public ConditionPool and(ConditionPool pool) {
        this.pool = pool;
        return this;
    }

    public ConditionPool or(ConditionPool pool) {
        this.pool = pool;
        return this;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public ConditionPool getPool() {
        return pool;
    }
}
