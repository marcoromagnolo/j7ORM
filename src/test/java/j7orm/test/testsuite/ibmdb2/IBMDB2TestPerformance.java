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

package j7orm.test.testsuite.ibmdb2;

import j7orm.DBConnection;
import j7orm.DBFactory;
import j7orm.exception.ConnectionException;
import j7orm.test.service.ServiceException;
import j7orm.test.testcase.PrintUtil;
import j7orm.test.testcase.TestPerformance;
import j7orm.type.DBType;
import org.junit.*;
import org.junit.runners.MethodSorters;

/**
 * @author Marco Romagnolo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IBMDB2TestPerformance {

    private static TestPerformance test;

    @BeforeClass
    public static void configure() throws ConnectionException {
        PrintUtil.suite(IBMDB2TestPerformance.class.getName());
        try {
            Class.forName(DBType.IBMDB2.getDriver());
        } catch (ClassNotFoundException e) {
            Assume.assumeTrue("DB2 Driver not available [not mandatory]", false);
        }
        DBConnection connection = null;
        try {
            connection = DBFactory.newInstance(new IBMDB2Config()).getConnection();
        } catch (ConnectionException e) {
            Assume.assumeTrue("Connection to IBM DB2 database is not available [not mandatory]", false);
        }
        test = new TestPerformance(connection);
    }

    @Test
    public void test1() throws ServiceException {
        test.test1();
    }

    @AfterClass
    public static void disconnect() throws ConnectionException {
        DBFactory.getInstance().disconnect();
    }
}
