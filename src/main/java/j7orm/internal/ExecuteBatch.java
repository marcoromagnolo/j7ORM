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

import java.sql.*;

/**
 * @author Marco Romagnolo
 */
public class ExecuteBatch {

    public static int[] run(Connection connection, String[] queries) throws SQLException {
        Statement stmt = connection.createStatement();
        for (String query : queries) {
            stmt.addBatch(query);
        }
        int[] result = stmt.executeBatch();
        stmt.close();
        return result;
    }

    public static int[] run(Connection connection, String query, Object[][] values) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(query);
        for (int i=0; i<values.length; i++) {
            for (int j=0; j<values[i].length; j++) {
                Object value = values[i][j];
                pstmt.setObject(j, value);
            }
            pstmt.addBatch();
        }
        int[] result = pstmt.executeBatch();
        pstmt.close();
        return result;
    }

    public static int[] call(Connection connection, String query, Object[][] values) throws SQLException {
        CallableStatement cstmt = connection.prepareCall(query);
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                Object value = values[i][j];
                cstmt.setObject(j, value);
            }
            cstmt.addBatch();
        }
        int[] result = cstmt.executeBatch();
        cstmt.close();
        return result;
    }

}
