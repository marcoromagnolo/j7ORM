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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marco Romagnolo
 */
public class Conditions {

    private List<ConditionPool> pools = new ArrayList<>();
    private List<Condition> list = new ArrayList<>();

    public Conditions(Condition condition) {
        pools.add(new ConditionPool(condition));
        list.add(condition);
    }

    public Conditions(ConditionPool pool) {
        pools.add(pool);
        list.addAll(pool.getConditions());
    }

    public Conditions and(Condition condition) {
        ConditionPool pool = new ConditionPool(condition);
        pools.add(pool);
        list.add(condition);
        return this;
    }

    public Conditions or(Condition condition) {
        ConditionPool pool = new ConditionPool(condition);
        pools.add(pool);
        list.add(condition);
        return this;
    }

    public Conditions and(ConditionPool pool) {
        pools.add(pool);
        list.addAll(pool.getConditions());
        return this;
    }

    public Conditions or(ConditionPool pool) {
        pools.add(pool);
        list.addAll(pool.getConditions());
        return this;
    }

    public List<ConditionPool> getPools() {
        return pools;
    }

    public List<Condition> getList() {
        return list;
    }

    private void recursivePool(StringBuilder sb, boolean isFirst, ConditionPool pool) {
        if (pool.getPool()!=null) {
            recursivePool(sb, true, pool.getPool());
        } else {
            if (isFirst) {
                iterateConditions(sb, pool.getConditions());
            } else {
                sb.append(pool.getLogic()).append(" (");
                iterateConditions(sb, pool.getConditions());
                sb.append(") ");
            }
        }
    }

    private void iterateConditions(StringBuilder sb, List<Condition> conditions) {
        boolean isFirst = true;
        for (Condition condition : conditions) {
            if (isFirst) {
                sb.append(condition);
            } else {
                sb.append(condition.getLogic()).append(" ").append(condition).append(" ");
            }
            isFirst = false;
        }
    }

    public String asSQL() {
        final StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (ConditionPool pool : pools) {
            recursivePool(sb, isFirst, pool);
            isFirst = false;
        }
        return sb.toString();
    }
}
