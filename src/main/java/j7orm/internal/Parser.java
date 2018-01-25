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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Parse ColumnData
 *
 * @author Marco Romagnolo
 */
public class Parser {

    public static Object parse(Object value) {
        if (value instanceof Date) {
            return date((Date) value);
        } else if (value instanceof Boolean) {
            return (boolean)value ? 1 : 0;
        }
        return value;
    }

    public static String date(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(date);
    }
}
