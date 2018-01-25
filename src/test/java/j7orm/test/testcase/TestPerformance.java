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
import j7orm.test.service.PerformanceService;
import j7orm.test.service.ServiceException;

/**
 * @author Marco Romagnolo
 */
public class TestPerformance {

    private final PerformanceService productService;

    public TestPerformance(DBConnection connection) {
        productService = new PerformanceService(connection);
    }

    public void test1() throws ServiceException {
        PrintUtil.test("Performance - Test1");
        productService.test1();
    }

}
