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

import j7orm.DBFactory;
import j7orm.exception.ConnectionException;
import j7orm.exception.OrmException;
import j7orm.test.testcase.PrintUtil;
import j7orm.test.testcase.TestDropTables;
import j7orm.type.DBType;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;

/**
 * @author Marco Romagnolo
 */
public class OracleDropTables {

    private static TestDropTables test;

    @BeforeClass
    public static void connect() throws ConnectionException {
        PrintUtil.suite(OracleDropTables.class.getName());
        try {
            Class.forName(DBType.ORACLE.getDriver());
        } catch (ClassNotFoundException e) {
            Assume.assumeTrue("Oracle Driver not available [not mandatory]", false);
        }
        Connection connection = null;
        try {
            connection = DBFactory.newInstance(new OracleConfig()).getConnection();
        } catch (ConnectionException e) {
            Assume.assumeTrue("Connection to Oracle database is not available [not mandatory]", false);
        }
        test = new TestDropTables(connection);
    }

    @Test
    public void drop() throws OrmException {
        test.drop("ORACLE");
    }

    @AfterClass
    public static void disconnect() throws ConnectionException {
        DBFactory.getInstance().disconnect();
    }
}
