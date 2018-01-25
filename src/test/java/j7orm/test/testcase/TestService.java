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

package j7orm.test.testcase;

import j7orm.DBConnection;
import j7orm.test.service.ProductService;
import j7orm.test.service.ServiceException;

/**
 * @author Marco Romagnolo
 */
public class TestService {

    private final ProductService productService;

    public TestService(DBConnection connection) {
        productService = new ProductService(connection);
    }

    public void insert() throws ServiceException {
        PrintUtil.test("Service - Insert");
        productService.insert();
    }

    public void update() throws ServiceException {
        PrintUtil.test("Service - Update");
        productService.update();
    }

    public void save() throws ServiceException {
        PrintUtil.test("Service - Save");
        productService.save();
    }

    public void find() throws ServiceException {
        PrintUtil.test("Service - Find");
        productService.find();
    }

    public void findAll() throws ServiceException {
        PrintUtil.test("Service - FindAll");
        productService.findAll();
    }

    public void delete() throws ServiceException {
        PrintUtil.test("Service - Delete");
        productService.delete();
    }

    public void deleteAll() throws ServiceException {
        PrintUtil.test("Service - DeleteAll");
        productService.deleteAll();
    }
}
