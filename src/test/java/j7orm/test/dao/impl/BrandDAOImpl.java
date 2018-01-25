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

package j7orm.test.dao.impl;

import j7orm.AbstractDAO;
import j7orm.DBConnection;
import j7orm.exception.OrmException;
import j7orm.internal.Condition;
import j7orm.internal.Conditions;
import j7orm.test.dao.BrandDAO;
import j7orm.test.entity.BrandEntity;

import java.util.List;

/**
 * @author Marco Romagnolo
 */
public class BrandDAOImpl extends AbstractDAO<BrandEntity> implements BrandDAO<BrandEntity> {

    public BrandDAOImpl(DBConnection connection) {
        super(connection);
    }

    @Override
    public BrandEntity findByUserId(Integer userId) throws OrmException {
        try {
            Conditions conditions = new Conditions(new Condition(BrandEntity.class, "userId", userId));
            List<BrandEntity> entities = find(conditions);
            return entities.get(1);
        } catch (Exception e) {
            throw new OrmException(e);
        }
    }

    @Override
    public BrandEntity findByUsername(String username) throws OrmException {
        try {
            Conditions conditions = new Conditions(new Condition(BrandEntity.class, "username", username));
            List<BrandEntity> entities = find(conditions);
            return entities.get(1);
        } catch (Exception e) {
            throw new OrmException(e);
        }
    }

    @Override
    public BrandEntity findByEmail(String email) throws OrmException {
        try {
            Conditions conditions = new Conditions(new Condition(BrandEntity.class, "email", email));
            List<BrandEntity> entities = find(conditions);
            return entities.get(1);
        } catch (Exception e) {
            throw new OrmException(e);
        }
    }

}
