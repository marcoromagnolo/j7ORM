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

package j7orm.type;

/**
 * @author Marco Romagnolo
 */
public enum OperatorType {
    eq("="), lt("<"), gt(">"), le("<="), ge(">=");


    private final String s;

    OperatorType(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }
}
