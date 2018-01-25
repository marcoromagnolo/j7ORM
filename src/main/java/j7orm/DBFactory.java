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

import j7orm.internal.DBImpl;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author Marco Romagnolo
 */
public class DBFactory {

    private static final Logger LOGGER = Logger.getLogger(DBFactory.class.getName());
    private static DB instance;

    private DBFactory() {}

    public static DB newInstance(DBConfig config) {
        instance = new DBImpl(config);
        return instance;
    }

    public static DB newInstance(Properties properties) {
        return newInstance(new DBConfig(properties));
    }

    public static DB newInstance(InputStream stream) {
        return newInstance(new DBConfig(stream));
    }

    public static DB newInstance() {
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("advantorm.properties");
        return newInstance(stream);
    }

    public static DB getInstance() {
        if (instance == null) {
            newInstance();
        }
        return instance;
    }
}
