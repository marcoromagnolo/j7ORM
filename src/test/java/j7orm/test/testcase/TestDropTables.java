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

package j7orm.test.testcase;

import j7orm.Query;
import j7orm.exception.OrmException;

import java.io.InputStream;
import java.sql.Connection;

/**
 * @author Marco Romagnolo
 */
public class TestDropTables {

    private final Connection connection;

    public TestDropTables(Connection connection) {
        this.connection = connection;
    }

    public void drop(String fileName) throws OrmException {
        PrintUtil.test("Drop tables");
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("sql/" + fileName + "/drop.sql");
        Query.run(connection, inputStream, true);
    }
}
