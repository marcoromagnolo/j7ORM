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

package j7orm.test;

import j7orm.test.testcase.TestFunctions;
import io.advant.orm.test.testsuite.*;
import j7orm.test.testsuite.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Marco Romagnolo
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestFunctions.class,
        DerbySuite.class,
        H2Suite.class,
        HSQLDBSuite.class,
        SQLiteSuite.class,
        MysqlSuite.class,
        PostgresqlSuite.class,
        OracleSuite.class,
        IBMDB2Suite.class,
})
public class TestSuite {
}
