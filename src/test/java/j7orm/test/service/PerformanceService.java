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

package j7orm.test.service;

import j7orm.DBConnection;
import j7orm.test.dao.BrandDAO;
import j7orm.test.dao.CategoryDAO;
import j7orm.test.dao.ProductDAO;
import j7orm.test.dao.impl.BrandDAOImpl;
import j7orm.test.dao.impl.CategoryDAOImpl;
import j7orm.test.dao.impl.ProductDAOImpl;
import j7orm.test.entity.BrandEntity;
import j7orm.test.entity.CategoryEntity;
import j7orm.test.entity.ProductEntity;

import java.util.logging.Logger;

/**
 * @author Marco Romagnolo
 */
public class PerformanceService {

    private static final Logger LOGGER = Logger.getLogger(PerformanceService.class.getName());
    private BrandDAO<BrandEntity> brandDAO;
    private CategoryDAO<CategoryEntity> categoryDAO;
    private ProductDAO<ProductEntity> productDAO;

    public PerformanceService(DBConnection connection) {
        brandDAO = new BrandDAOImpl(connection);
        categoryDAO = new CategoryDAOImpl(connection);
        productDAO = new ProductDAOImpl(connection);
    }

    public void test1() throws ServiceException {

    }

}
