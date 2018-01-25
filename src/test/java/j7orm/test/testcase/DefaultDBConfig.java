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

package j7orm.test.testcase;

import j7orm.DBConfig;
import j7orm.test.entity.BrandEntity;
import j7orm.test.entity.CategoryEntity;
import j7orm.test.entity.ProductCategoryEntity;
import j7orm.test.entity.ProductEntity;
import j7orm.type.DBType;

import java.util.HashSet;

/**
 * @author Marco Romagnolo
 */
public class DefaultDBConfig extends DBConfig {

    public static final String HOST = "localhost";
    public static final String DATABASE = "advantorm";
    public static final String USER = "advantorm";
    public static final String PASSWORD = "advantorm";

    public DefaultDBConfig(DBType dbType, String database, String user, String password) {
        super(dbType, database, user, password);
        configure();
    }

    public DefaultDBConfig(DBType dbType, String host, int port, String database, String user, String password) {
        super(dbType, host, port, database, user, password);
        configure();
    }

    private void configure() {
        HashSet<String> entities = new HashSet<>();
        entities.add(BrandEntity.class.getName());
        entities.add(CategoryEntity.class.getName());
        entities.add(ProductCategoryEntity.class.getName());
        entities.add(ProductEntity.class.getName());
        setEntities(entities);
    }

}
