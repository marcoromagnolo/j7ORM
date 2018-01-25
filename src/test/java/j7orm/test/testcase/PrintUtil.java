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

/**
 * @author Marco Romagnolo
 */
public class PrintUtil {

    private static final String sep = " -> ";
    private static String suite;
    private static String test;
    private static String action;
    private static String result;

    public static void suite(String value) {
        suite = "Suite: " + value;
        System.out.println("\n" + suite);
    }

    public static void test(String value) {
        test = "Test: " + value;
        System.out.println(suite + sep + test);
    }

    public static void action(String value) {
        action = value;
        System.out.println(suite + sep + test + sep + action);
    }

    public static void result(String value) {
        result = value;
        System.out.println(suite + sep + test + sep + action + sep + result);
    }
}
