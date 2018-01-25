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

package j7orm.test.entity;

import j7orm.Entity;
import j7orm.test.table.BrandTable;

/**
 * @author Marco Romagnolo
 */
public class BrandEntity extends BrandTable implements Entity {

    @Override
    public String toString() {
        return "BrandEntity{} " + super.toString();
    }

}
