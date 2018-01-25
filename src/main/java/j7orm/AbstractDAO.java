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

package j7orm;

import j7orm.exception.OrmException;
import j7orm.exception.TableParseException;
import j7orm.exception.UnsynchronizedException;
import j7orm.internal.*;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @param <T>
 *
 * @author Marco Romagnolo
 */
public abstract class AbstractDAO<T extends Entity> implements DAO<T> {

	private static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
	private final DBConnection connection;
	private final Class<T> entityClass;
    private SqlProcessor sqlProcessor;
	private EntityConverter<T> converter;

	protected AbstractDAO(DBConnection connection) {
		this.connection = connection;
		this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		loadEntities();
	}

	AbstractDAO(Class<T> entityClass, DBConnection connection) {
        this.connection = connection;
		this.entityClass = entityClass;
		loadEntities();
	}

	private void loadEntities() {
		try {
			EntityReflect<T> reflect = EntityReflect.getInstance(entityClass);
			converter = new EntityConverter<>(entityClass, reflect);
			sqlProcessor = new SqlProcessor(connection, reflect);
		} catch (TableParseException | NoSuchFieldException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	protected Connection getConnection() {
		return connection;
	}

	protected void close() {
		try {
            sqlProcessor.close();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

    @Override
    public int deleteAll() throws OrmException {
        try {
			return sqlProcessor.deleteAll();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new OrmException(e);
        } finally {
			close();
		}
	}

	@Override
	public void insert(T entity) throws OrmException {
		try {
			sqlProcessor.insert(entity, fromEntity(entity));
		} catch (SQLException | IllegalAccessException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new OrmException(e);
		} finally {
			close();
		}
	}

    @Override
	public int update(T entity) throws OrmException {
		try {
			return sqlProcessor.update(entity, fromEntity(entity));
		} catch (SQLException | IllegalAccessException | UnsynchronizedException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new OrmException(e);
		} finally {
			close();
		}
	}

	@Override
	public Integer save(T entity) throws OrmException {
		try {
            Integer result = null;
			Conditions conditions = new Conditions(new Condition(entityClass, "id", (entity).getId()));
			ResultSet resultSet = sqlProcessor.select(conditions);
			// Set Id and Version table column name
            String tableIndex = EntityReflect.getInstance(entityClass).getTableIndex();
            String columnNameId = EntityConverter.getColumnName(tableIndex, "id");
            String columnNameVersion = EntityConverter.getColumnName(tableIndex, "version");
            Long id = null;
            Long version = null;
            if (resultSet.next()) {
                id = resultSet.getLong(columnNameId);
                version = resultSet.getLong(columnNameVersion);
			}
            entity.setId(id);
            entity.setVersion(version);
			List<ColumnData> columns = fromEntity(entity);
			if (id==null) {
				sqlProcessor.insert(entity, columns);
			} else {
                result = sqlProcessor.update(entity, columns);
			}
            return result;
		} catch (SQLException | IllegalAccessException | TableParseException | NoSuchFieldException | UnsynchronizedException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new OrmException(e);
		} finally {
			close();
		}
	}

	@Override
	public int delete(T entity) throws OrmException {
		try {
			return sqlProcessor.delete(entity);
		} catch (SQLException | UnsynchronizedException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new OrmException(e);
		} finally {
			close();
		}
	}

    @Override
    public int delete(Conditions conditions) throws OrmException {
        try {
            return sqlProcessor.delete(conditions);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new OrmException(e);
        } finally {
            close();
        }
    }

	@Override
	public List<T> findAll() throws OrmException {
		try {
            ResultSet rs = sqlProcessor.select(null);
            return toEntities(rs);
		} catch (TableParseException | NoSuchFieldException | SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new OrmException(e);
		}finally {
			close();
		}
	}

    @Override
	public T find(Long id) throws OrmException {
        try {
            Conditions conditions = new Conditions(new Condition(entityClass, "id", id));
            ResultSet rs = sqlProcessor.select(conditions);
            return toEntity(rs);
        } catch (TableParseException | NoSuchFieldException | SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new OrmException(e);
        } finally {
            close();
        }
	}

	@Override
	public List<T> find(Conditions conditions) throws OrmException {
		try {
			ResultSet rs = sqlProcessor.select(conditions);
			return toEntities(rs);
		} catch (TableParseException | NoSuchFieldException | SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new OrmException(e);
		} finally {
			close();
		}
	}

    protected T toEntity(ResultSet rs) throws OrmException {
		try {
			return converter.toEntity(rs);
		} catch (IllegalAccessException | TableParseException | SQLException | InstantiationException | NoSuchFieldException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new OrmException(e);
		}
	}

    protected List<T> toEntities(ResultSet rs) throws OrmException {
		try {
			return converter.toEntities(rs);
		} catch (IllegalAccessException | NoSuchFieldException | SQLException | InstantiationException | TableParseException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new OrmException(e);
		}
	}

    protected List<ColumnData> fromEntity(T entity) throws OrmException {
		try {
			return converter.fromEntity(entity);
		} catch (IllegalAccessException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new OrmException(e);
		}
	}

}
