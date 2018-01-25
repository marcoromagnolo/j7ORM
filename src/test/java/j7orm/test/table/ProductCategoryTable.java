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

package j7orm.test.table;

import j7orm.AbstractTable;
import j7orm.annotation.Column;
import j7orm.annotation.Table;

/**
 * @author Marco Romagnolo
 */
@Table(name = "test_product_category")
public class ProductCategoryTable extends AbstractTable {

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "category_id")
    private Long categoryId;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "ProductCategoryTable{" +
                "productId=" + productId +
                ", categoryId=" + categoryId +
                "} " + super.toString();
    }
}
