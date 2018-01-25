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

package j7orm.internal;

import j7orm.annotation.Column;
import j7orm.annotation.Relation;
import j7orm.annotation.Table;
import j7orm.exception.TableParseException;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * EntityReflect
 * @param <T>
 *
 * @author Marco Romagnolo
 */
public class EntityReflect<T> {

    private static Map<Class<?>, EntityReflect> instance = new HashMap<>();
    private static int num = 0;
    private final Class<T> entityClass;
    private final String tableIndex;
    private final Class<? super T> tableClass;
    private final Class<? super T> superTableClass;
    private final String table;
    private Field idField;
    private Set<JoinData> joins = new HashSet<>();
    private Set<ColumnData> columns = new HashSet<>();
    private Map<Field, Class> joinEntities = new HashMap<>();

    private EntityReflect(Class<T> entityClass, int tableIndex) throws TableParseException, NoSuchFieldException {
        this.entityClass = entityClass;
        this.tableIndex = "t" + tableIndex;
        this.tableClass = getTableFromEntity(entityClass);
        this.superTableClass = tableClass.getSuperclass();
        this.table = tableClass.getAnnotation(Table.class).name();
        parseTable();
        parseEntity();
    }

    public static <T> EntityReflect<T> getInstance(Class<T> clazz) throws TableParseException, NoSuchFieldException {
        EntityReflect<T> current = instance.get(clazz);
        if (current==null) {
            current = new EntityReflect<>(clazz, ++num);
            instance.put(clazz, current) ;
        }
        return current;
    }

    private void parseEntity() throws NoSuchFieldException, TableParseException {
        for (Field field : entityClass.getDeclaredFields()) {
            field.setAccessible(true);
            Relation annotRelation = field.getAnnotation(Relation.class);
            if (annotRelation!=null) {
                Class joinEntityClass;
                if (field.getType().equals(List.class) || field.getType().equals(Set.class)) {
                    ParameterizedType genericType = (ParameterizedType)  field.getGenericType();
                    joinEntityClass = (Class<?>) genericType.getActualTypeArguments()[0];
                } else {
                    joinEntityClass = field.getType();
                }
                Class joinTableClass = getTableFromEntity(joinEntityClass);
                Table annotJoinTable = (Table) joinTableClass.getAnnotation(Table.class);
                String joinTable = annotJoinTable.name();
                String column = getColumnFromTable(tableClass, annotRelation.from());
                String joinColumn = getColumnFromTable(joinTableClass, annotRelation.to());
                JoinData joinData = new JoinData(table, column, joinTable, joinColumn);
                joins.add(joinData);
                joinEntities.put(field, joinEntityClass);
            }
        }
    }

    private String getColumnFromTable(Class tableClass, String property) throws NoSuchFieldException {
        Field field;
        if (property.equals("id") || property.equals("version")) {
            field = tableClass.getSuperclass().getDeclaredField(property);
        } else {
            field = tableClass.getDeclaredField(property);
        }
        field.setAccessible(true);
        return field.getAnnotation(Column.class).name();
    }

    private void parseTable() throws NoSuchFieldException {
        for (Field field : tableClass.getDeclaredFields()) {
            field.setAccessible(true);
            Column annotColumn = field.getAnnotation(Column.class);
            String column = annotColumn.name();
            ColumnData columnData = new ColumnData(false, false, column, table, tableIndex, field);
            columns.add(columnData);
        }
        idField = superTableClass.getDeclaredField("id");
        idField.setAccessible(true);
        ColumnData idColumnData = new ColumnData(true, false, "id", table, tableIndex, idField);
        columns.add(idColumnData);
        Field versionField = superTableClass.getDeclaredField("version");
        versionField.setAccessible(true);
        ColumnData versionColumnData = new ColumnData(false, true, "version", table, tableIndex, versionField);
        columns.add(versionColumnData);
    }

    public String getColumnFromProperty(String property) throws NoSuchFieldException {
        return getColumnFromTable(tableClass, property);
    }

    private <E> Class<? super E> getTableFromEntity(Class<E> entityClass) throws TableParseException {
        Table tableAnnot;
        Class<? super E> tableClass;
        do {
            tableClass = entityClass.getSuperclass();
            tableAnnot = tableClass.getAnnotation(Table.class);
            if (tableClass.equals(Object.class)) {
                throw new TableParseException("Error parsing Table Annotations");
            }
        } while (tableAnnot==null);
        return tableClass;
    }

    public Field getIdField() {
        return idField;
    }

    public String getTable() {
        return table;
    }

    public String getTableIndex() {
        return tableIndex;
    }

    public Set<JoinData> getJoins() {
        return joins;
    }

    public Set<ColumnData> getColumns() {
        return columns;
    }

    public Map<Field, Class> getJoinEntities() {
        return joinEntities;
    }

}
