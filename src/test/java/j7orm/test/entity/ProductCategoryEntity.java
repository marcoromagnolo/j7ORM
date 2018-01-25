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

package j7orm.test.entity;

import j7orm.Entity;
import j7orm.annotation.Relation;
import j7orm.test.table.ProductCategoryTable;

/**
 * @author Marco Romagnolo
 */
public class ProductCategoryEntity extends ProductCategoryTable implements Entity {

    @Relation(from = "categoryId" , to = "id")
    private CategoryEntity category;

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ProductCategoryEntity{" +
                "category=" + category +
                "} " + super.toString();
    }
}
