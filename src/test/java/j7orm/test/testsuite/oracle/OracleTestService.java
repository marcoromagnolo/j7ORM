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

package j7orm.test.testsuite.oracle;

import j7orm.DBConnection;
import j7orm.DBFactory;
import j7orm.exception.ConnectionException;
import j7orm.test.service.ServiceException;
import j7orm.test.testcase.PrintUtil;
import j7orm.test.testcase.TestService;
import j7orm.type.DBType;
import org.junit.*;
import org.junit.runners.MethodSorters;

/**
 * @author Marco Romagnolo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OracleTestService {

    private static TestService test;

    @BeforeClass
    public static void configure() throws ConnectionException {
        PrintUtil.suite(OracleTestService.class.getName());
        try {
            Class.forName(DBType.ORACLE.getDriver());
        } catch (ClassNotFoundException e) {
            Assume.assumeTrue("Oracle Driver not available [not mandatory]", false);
        }
        DBConnection connection = null;
        try {
            connection = DBFactory.newInstance(new OracleConfig()).getConnection();
        } catch (ConnectionException e) {
            Assume.assumeTrue("Connection to Oracle database is not available [not mandatory]", false);
        }
        test = new TestService(connection);
    }

    @Test
    public void test1_insert() throws ServiceException {
        test.insert();
    }

    @Test
    public void test2_findAll() throws ServiceException {
        test.findAll();
    }

    @Test
    public void test3_find() throws ServiceException {
        test.find();
    }

    @Test
    public void test4_update() throws ServiceException {
        test.update();
    }

    @Test
    public void test5_save() throws ServiceException {
        test.save();
    }

    @Test
    public void test6_delete() throws ServiceException {
        test.delete();
    }

    @Test
    public void test7_deleteAll() throws ServiceException {
        test.deleteAll();
    }

    @AfterClass
    public static void disconnect() throws ConnectionException {
        DBFactory.getInstance().disconnect();
    }
}
