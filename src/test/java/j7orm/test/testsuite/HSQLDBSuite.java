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

package j7orm.test.testsuite;

import j7orm.test.testsuite.hsqldb.HSQLDBCreateTables;
import j7orm.test.testsuite.hsqldb.HSQLDBDropTables;
import j7orm.test.testsuite.hsqldb.HSQLDBTestDAO;
import j7orm.test.testsuite.hsqldb.HSQLDBTestService;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Marco Romagnolo
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        HSQLDBCreateTables.class,
        HSQLDBTestDAO.class,
        HSQLDBTestService.class,
        HSQLDBDropTables.class
})
public class HSQLDBSuite {
}
