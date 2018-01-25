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

package j7orm;

import j7orm.exception.ConnectionException;
import j7orm.exception.OrmException;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marco Romagnolo
 */
public class Transaction {

    private static final Logger LOGGER = Logger.getLogger(Transaction.class.getName());
    private DBConnection connection;

    public Transaction() {
        try {
            connection = DBFactory.getInstance().getConnection();
        } catch (ConnectionException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public Transaction(DBConnection connection) {
        this.connection = connection;
    }

    public Transaction(String connectionId) {
        try {
            this.connection = DBMultiFactory.getInstance(connectionId).getConnection();
        } catch (ConnectionException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static boolean isAutoCommit(DBConnection connection) throws OrmException {
        return new Transaction(connection).isAutoCommit();
    }

    public static boolean isAutoCommit(String connectionId) throws OrmException {
        return new Transaction(connectionId).isAutoCommit();
    }

    public boolean isAutoCommit() throws OrmException {
        try {
            return connection.getAutoCommit();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new OrmException(e);
        }
    }

    public static void setAutoCommit(DBConnection connection, boolean autoCommit) throws OrmException {
        new Transaction(connection).setAutoCommit(autoCommit);
    }

    public static void setAutoCommit(String connectionId, boolean autoCommit) throws OrmException {
        new Transaction(connectionId).setAutoCommit(autoCommit);
    }

    public void setAutoCommit(boolean autoCommit) throws OrmException {
        try {
            connection.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new OrmException(e);
        }
    }

    public static void commit(DBConnection connection) throws OrmException {
        new Transaction(connection).commit();
    }

    public static void commit(String connectionId) throws OrmException {
        new Transaction(connectionId).commit();
    }

    public void commit() throws OrmException {
        try {
            connection.commit();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new OrmException(e);
        }
    }

    public static void rollback(DBConnection connection) throws OrmException {
        new Transaction(connection).rollback();
    }

    public static void rollback(String connectionId) throws OrmException {
        new Transaction(connectionId).rollback();
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

}
