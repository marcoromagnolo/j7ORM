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

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Marco Romagnolo
 */
public class DBMultiFactory {

    private static final Logger LOGGER = Logger.getLogger(DBFactory.class.getName());
    private static Map<String, DB> instances = new HashMap<>();

    private DBMultiFactory() {}

    public void add(String key, DBConfig config) {
        instances.put(key, new DBImpl(config));
    }

    public void remove(String key) {
        if (instances.containsKey(key)) {
            instances.get(key).disconnect();
            instances.remove(key);
        }
    }

    public static Map<String, DB> getInstances() {
        return instances;
    }

    public static DB getInstance(String key) {
        return instances.get(key);
    }
}
