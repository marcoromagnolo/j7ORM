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

package j7orm;

import j7orm.exception.OrmException;
import j7orm.internal.ExecuteBatch;
import j7orm.internal.ExecuteUpdate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marco Romagnolo
 */
public class Query {

    private static final Logger LOGGER = Logger.getLogger(Query.class.getName());

    public static void run(Connection connection, String sql) throws OrmException {
        try {
            ExecuteUpdate.run(connection, sql);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new OrmException(e);
        }
    }

    public static void call(Connection connection, String sql) throws OrmException {
        try {
            ExecuteUpdate.call(connection, sql);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new OrmException(e);
        }
    }

    public static void run(Connection connection, InputStream inputStream, boolean exitOnError) throws OrmException {
        try {
            String[] array = streamToString(inputStream).split("[\\r\\n]");
            ExecuteUpdate.runScript(connection, array, exitOnError);
        } catch (IOException | SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new OrmException(e);
        }
    }

    public static void call(Connection connection, InputStream inputStream) throws OrmException {
        try {
            ExecuteUpdate.call(connection, streamToString(inputStream));
        } catch (IOException | SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new OrmException(e);
        }
    }

    public static int[] batch(Connection connection, String separator, InputStream inputStream) throws OrmException {
        try {
            return ExecuteBatch.run(connection, streamToString(inputStream).split(separator));
        } catch (IOException | SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new OrmException(e);
        }
    }

    public static int[] batch(Connection connection, String queries[]) throws OrmException {
        try {
            return ExecuteBatch.run(connection, queries);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new OrmException(e);
        }
    }

    public static int[] batch(Connection connection, String query, Object[][] values) throws OrmException {
        try {
            return ExecuteBatch.run(connection, query, values);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new OrmException(e);
        }
    }

    public static int[] callBatch(Connection connection, String query, Object values[][]) throws OrmException {
        try {
            return ExecuteBatch.call(connection, query, values);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new OrmException(e);
        }
    }

    private static String streamToString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            byteArray.write(buffer, 0, length);
        }
        return byteArray.toString("UTF-8");
    }

}
