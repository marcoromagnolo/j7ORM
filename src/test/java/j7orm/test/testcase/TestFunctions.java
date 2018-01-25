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

import j7orm.DB;
import j7orm.DBFactory;
import j7orm.exception.ConnectionException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Marco Romagnolo
 */
public class TestFunctions {

    @Test
    public void testDBstream() throws ConnectionException {
        DB db = DBFactory.newInstance(Thread.currentThread().getContextClassLoader().getResourceAsStream("customconfig.properties"));
        Assert.assertFalse(db.isConnected());
        Assert.assertEquals(DBFactory.getInstance(), db);
        Assert.assertEquals(db.getConnection(), DBFactory.getInstance().getConnection());
        Assert.assertTrue(db.isConnected());
        db.disconnect();
        Assert.assertFalse(db.isConnected());
    }

    @Test
    public void testDBauto() throws ConnectionException {
        DB db = DBFactory.newInstance();
        Assert.assertFalse(db.isConnected());
        Assert.assertEquals(DBFactory.getInstance(), db);
        Assert.assertEquals(db.getConnection(), DBFactory.getInstance().getConnection());
        Assert.assertTrue(db.isConnected());
        db.disconnect();
        Assert.assertFalse(db.isConnected());
    }

}
