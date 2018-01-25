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

import j7orm.AbstractTable;
import j7orm.Entity;
import j7orm.exception.TableParseException;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Converter from JDBC ResultSet to Entity and from Entity to JDBC ResultSet
 * @param <T>
 *
 * @author Marco Romagnolo
 */
public class EntityConverter<T extends Entity> {

    private static final Logger LOGGER = Logger.getLogger(EntityConverter.class.getName());
    private final Class<T> entityClass;
    private final EntityReflect<T> entityReflect;

    /**
     * Constructor
     * @param entityClass the Class of an entity
     * @param entityReflect the EntityReflect instance
     */
    public EntityConverter(Class<T> entityClass, EntityReflect<T> entityReflect) {
        this.entityClass = entityClass;
        this.entityReflect = entityReflect;
    }

    /**
     * Convert from result set to an entity
     * @param resultSet JDBC result obtained from a query
     * @return the entity with all fields set ad all entity children recursively
     */
    public T toEntity(ResultSet resultSet) throws SQLException, IllegalAccessException, InstantiationException, NoSuchFieldException, TableParseException {
        T entity = null;
        if (resultSet.next()) {
            entity = entityClass.newInstance();
            setEntityColumns(resultSet, entityReflect.getColumns(), entity);
            setEntityJoins(resultSet, entity, entityReflect);
            while (resultSet.next()) {
                setEntityJoins(resultSet, entity, entityReflect);
            }
            ((AbstractTable)entity).setLastId(entity.getId());
        }
        return entity;
    }

    /**
     * Recursively method to fill all entity join fields
     * @param resultSet passed from toEntity()
     * @param currentEntity the current generic Entity
     * @param currentReflect the current EntityReflect
     * @param <E> recursive Entity of joins
     * @return recursive Entity
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private <E extends Entity> E setEntityJoins(ResultSet resultSet, E currentEntity, EntityReflect<E> currentReflect) throws IllegalAccessException, InstantiationException, SQLException, NoSuchFieldException, TableParseException {
        if (currentReflect.getJoinEntities()!=null) {
            for (Map.Entry<Field, Class> joinEntry : currentReflect.getJoinEntities().entrySet()) {
                Field field = joinEntry.getKey();
                field.setAccessible(true);
                Class<E> joinClass = joinEntry.getValue();
                EntityReflect<E> joinReflect = EntityReflect.getInstance(joinClass);
                if (field.getType().equals(List.class)) {
                    List<E> list = (List<E>) field.get(currentEntity);
                    if (list == null) {
                        list = new ArrayList<>();
                        field.set(currentEntity, list);
                    }
                    E joinEntity = joinClass.newInstance();
                    joinEntity.setId(getIdValue(joinReflect, resultSet));
                    int index = list.indexOf(joinEntity);
                    if (index == -1) {
                        setEntityColumns(resultSet, joinReflect.getColumns(), joinEntity);
                        list.add(joinEntity);
                    } else {
                        joinEntity = list.get(index);
                    }
                    setEntityJoins(resultSet, joinEntity, joinReflect);
                } else if (field.getType().equals(Set.class)) {
                    Set<E> set = (Set<E>) field.get(currentEntity);
                    if (set == null) {
                        set = new HashSet<>();
                        field.set(currentEntity, set);
                    }
                    E joinEntity = null;
                    for (E e : set) {
                        if (e.getId().equals(getIdValue(joinReflect, resultSet))) {
                            joinEntity = e;
                            break;
                        }
                    }
                    if (joinEntity==null) {
                        joinEntity = joinClass.newInstance();
                        setEntityColumns(resultSet, joinReflect.getColumns(), joinEntity);
                        set.add(joinEntity);
                    }
                    setEntityJoins(resultSet, joinEntity, joinReflect);
                } else {
                    E joinEntity = (E) field.get(currentEntity);
                    if (joinEntity == null) {
                        joinEntity = joinClass.newInstance();
                        setEntityColumns(resultSet, joinReflect.getColumns(), joinEntity);
                    }
                    setEntityJoins(resultSet, joinEntity, joinReflect);
                    field.set(currentEntity, joinEntity);
                }
            }
        }
        return currentEntity;
    }

    /**
     * Set fields of a Table of an Entity from ResultSet and a Set of ColumnData
     * @param resultSet passed from toEntity()
     * @param columns Set of ColumnData
     * @param currentEntity entity to populate
     * @param <E> generic recursive entity
     * @throws SQLException
     * @throws IllegalAccessException
     */
    private <E> void setEntityColumns(ResultSet resultSet, Set<ColumnData> columns, E currentEntity) throws SQLException, IllegalAccessException {
        for (ColumnData columnData : columns) {
            Field field = columnData.getField();
            field.setAccessible(true);
            String columnName = getColumnName(columnData.getTableIndex(), columnData.getColumn());
            ValueBridge.setField(currentEntity, field, columnData.getValueType(), columnName, resultSet);
        }
    }

    /**
     * Convert in a List of Entity
     * @param resultSet from JDBC query
     * @return List of Entity
     */
    public List<T> toEntities(ResultSet resultSet) throws SQLException, IllegalAccessException, InstantiationException, NoSuchFieldException, TableParseException {
        ArrayList<T> list = new ArrayList<>();
        while (resultSet.next()) {
            T tmpEntity = entityClass.newInstance();
            Long currentId = getIdValue(entityReflect, resultSet);
            tmpEntity.setId(currentId);
            int index = list.indexOf(tmpEntity);
            if (index == -1) {
                T entity = entityClass.newInstance();
                setEntityColumns(resultSet, entityReflect.getColumns(), entity);
                setEntityJoins(resultSet, entity, entityReflect);
                list.add(entity);
            } else {
                T entity = list.get(index);
                setEntityJoins(resultSet, entity, entityReflect);
            }
        }
        return list;
    }

    /**
     * Convert from Entity to a List of ColumnData
     * Used to construct INSERT, UPDATE and DELETE query
     * @param entity take all Table fields without Entity fields
     * @return List of ColumnData
     */
    public List<ColumnData> fromEntity(T entity) throws IllegalAccessException {
        ArrayList<ColumnData> list = new ArrayList<>();
        if (entity!=null) {
            for(ColumnData columnData : entityReflect.getColumns()){
                Field field = columnData.getField();
                Object value = field.get(entity);
                columnData.setValue(value);
                list.add(columnData);
            }
        }
        return list;
    }

    /**
     * Return the name of table column
     * @param tableIndex String table index loaded from <code>EntityReflect</code>
     * @param column String name of a column
     * @return String composed with table_column
     */
    public static String getColumnName(String tableIndex, String column) {
        return tableIndex + "_" + column;
    }

    /**
     * Get the value of id directly from ResultSet
     * @param reflect EntityReflect
     * @param resultSet come from toEntity()
     * @return Long id of row
     * @throws SQLException
     */
    public Long getIdValue(EntityReflect reflect, ResultSet resultSet) throws SQLException {
        Field idField = reflect.getIdField();
        String idColumn = getColumnName(reflect.getTableIndex(), idField.getName());
        return resultSet.getLong(idColumn);
    }
}
