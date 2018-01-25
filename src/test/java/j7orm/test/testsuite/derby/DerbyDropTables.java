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

package j7orm.test.testsuite.derby;

import j7orm.DBFactory;
import j7orm.exception.ConnectionException;
import j7orm.exception.OrmException;
import j7orm.test.testcase.PrintUtil;
import j7orm.test.testcase.TestDropTables;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;

/**
 * @author Marco Romagnolo
 */
public class DerbyDropTables {

    private static TestDropTables test;

    @BeforeClass
    public static void connect() throws ConnectionException {
        PrintUtil.suite(DerbyDropTables.class.getName());
        Connection connection = DBFactory.newInstance(new DerbyConfig()).getConnection();
        test = new TestDropTables(connection);
    }

    @Test
    public void drop() throws ConnectionException, OrmException {
        test.drop("DERBY");
    }

    @AfterClass
    public static void disconnect() throws ConnectionException {
        DBFactory.getInstance().disconnect();
    }
}
