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

package j7orm.internal;

import j7orm.DB;
import j7orm.DBConfig;
import j7orm.DBConnection;
import j7orm.exception.ConnectionException;
import j7orm.exception.TableParseException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marco Romagnolo
 */
public class DBImpl implements DB {

    private static final Logger LOGGER = Logger.getLogger(DBImpl.class.getName());
    private final DBConfig config;
    private DataSource datasource;
    private DBConnection connection;

    public DBImpl(DBConfig config) {
        this.config = config;
        try {
            // Configure Driver Loading for JDBC
            Class.forName(config.getDriver());
            // Configure Entities
            for (String entity : config.getEntities()) {
                Class<?> entityClass = Class.forName(entity);
                EntityReflect.getInstance(entityClass);
            }
        } catch (ClassNotFoundException | TableParseException | NoSuchFieldException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public DataSource getDataSource() {
        if (datasource == null) {
            try {
                 datasource = (DataSource) new InitialContext().lookup(config.getDatasource());
            } catch (NamingException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }
        return datasource;
    }

    @Override
    public void connect() throws SQLException {
        Connection conn;
        if (config.getDatasource() != null) {
            conn = getDataSource().getConnection();
        } else {
            DBConnectionParams params = config.getParams();
            conn = DriverManager.getConnection(params.getUri(), params.getProperties());
        }
        connection = new DBConnection(conn, config.getDbType());
    }

    @Override
    public DBConnection getConnection() throws ConnectionException {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
        return connection;
    }

    @Override
    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            return false;
        }
    }

    @Override
    public void disconnect() {
        if (connection!=null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }
}
