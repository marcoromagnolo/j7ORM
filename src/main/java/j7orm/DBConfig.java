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

import j7orm.internal.DBConnectionParams;
import j7orm.type.DBType;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marco Romagnolo
 */
public class DBConfig {

    private static final Logger LOGGER = Logger.getLogger(DBConfig.class.getName());
    private String datasource;
    private DBType dbType;
    private String driver;
    private String host;
    private int port;
    private String database;
    private String user;
    private String password;
    private Properties properties;
    private Set<String> entities = new HashSet<>();

    public DBConfig(Properties configProps) {
        configureProperties(configProps);
    }

    public DBConfig(InputStream inputStream) {
        try {
            Properties configProps = new Properties();
            configProps.load(inputStream);
            configureProperties(configProps);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private void configureProperties(Properties configProps) {
        datasource = configProps.getProperty("datasource");
        dbType = DBType.valueOf(configProps.getProperty("db.type").toUpperCase());
        driver = dbType.getDriver();
        host = configProps.getProperty("db.host");
        port = Integer.valueOf(configProps.getProperty("db.port", "0"));
        database = configProps.getProperty("db.database");
        user = configProps.getProperty("db.user");
        password = configProps.getProperty("db.password");
        Enumeration enumKey = configProps.keys();
        while (enumKey.hasMoreElements()) {
            String key = (String) enumKey.nextElement();
            String dbProperty = "db.property.";
            if (key.startsWith(dbProperty)) {
                if (properties == null) {
                    properties = new Properties();
                }
                String value = configProps.getProperty(key);
                properties.put(key.substring(dbProperty.length()), value);
            } else if (key.startsWith("entity.")) {
                if (entities == null) {
                    entities = new HashSet<>();
                }
                String value = configProps.getProperty(key);
                entities.add(value);
            }
        }
    }

    public DBConfig(String datasource) {
        this.datasource = datasource;
    }

    public DBConfig(DBType dbType, String host, int port, String database, String user, String password) {
        this(dbType, database, user ,password);
        this.host = host;
        this.port = port;
    }

    public DBConfig(DBType dbType, String database, String user, String password) {
        this.driver = dbType.getDriver();
        this.dbType = dbType;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    public DBConnectionParams getParams() {
        if (properties == null) {
            properties = new Properties();
        }
        if (user != null) {
            properties.put("user", user);
        }
        if (password != null) {
            properties.put("password", password);
        }
        return new DBConnectionParams() {

            @Override
            public String getUri() {
                return getDBTypeUri();
            }

            @Override
            public Properties getProperties() {
                return properties;
            }
        };
    }

    private String getDBTypeUri() {
        switch (dbType) {
            case MYSQL:
                return "jdbc:mysql://" + host + ":" + port + "/" + database;
            case POSTGRESQL:
                return "jdbc:postgresql://" + host + ":" + port + "/" + database;
            case IBMDB2:
                return "jdbc:db2://" + host + ":" + port + "/" + database;
            case MSSQL:
                return "jdbc:microsoft:sqlserver://" + host + ":" + port + "/" + database;
            case SYBASE:
                return "jdbc:jtds:sybase://" + host + ":" + port + "/" + database;
            case ORACLE:
                return "jdbc:oracle:thin:@" + host + ":" + port + ":" + database;
            case DERBY:
                return "jdbc:derby:" + database;
            case H2:
                return "jdbc:h2:" + database;
            case HSQLDB:
                return "jdbc:hsqldb:" + database;
            case SQLITE:
                return "jdbc:sqlite:" + database;
            default:
                LOGGER.log(Level.SEVERE, "DBType not known or isn't a Local connection");
                return null;
        }
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public DBType getDbType() {
        return dbType;
    }

    public void setDbType(DBType dbType) {
        this.dbType = dbType;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Set<String> getEntities() {
        return entities;
    }

    public void setEntities(Set<String> entities) {
        this.entities = entities;
    }
}
