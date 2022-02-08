package ru.dvsokolov.myorm.jdbc.mapper;


import ru.dvsokolov.core.repository.DataTemplate;
import ru.dvsokolov.core.repository.executor.DbExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final ru.dvsokolov.myorm.jdbc.mapper.EntitySQLMetaData entitySQLMetaData;

    private static final Logger log = LoggerFactory.getLogger(DataTemplateJdbc.class);

    public DataTemplateJdbc(DbExecutor dbExecutor, ru.dvsokolov.myorm.jdbc.mapper.EntitySQLMetaData entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        var entity = dbExecutor.executeSelect(connection,
                entitySQLMetaData.getSelectByIdSql(),
                List.of(id),
                rs -> {
                    try {
                        if (rs.next()) {

                            return getEntity(rs);

                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                });

        return (Optional<T>) entity;
    }

    @Override
    public List<T> findAll(Connection connection) {
        var entityList = dbExecutor.executeSelect(connection,
                entitySQLMetaData.getSelectAllSql(),
                new ArrayList<>(),
                this::getEntityList);

        return entityList.orElseGet(ArrayList::new);
    }

    @Override
    public long insert(Connection connection, T entity) {
        List<Field> fieldList = entityClassMetaData.getFieldsWithoutId();
        List<Object> valueList = getValues(fieldList, entity);
        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), valueList);
    }

    @Override
    public void update(Connection connection, T entity) {
        List<Field> fieldList = entityClassMetaData.getFieldsWithoutId();
        List<Object> valueList = getValues(fieldList, entity);
        var idField = entityClassMetaData.getIdField();
        idField.setAccessible(true);
        try {
            var idValue = idField.get(entity);
            valueList.add(idValue);
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), valueList);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Entity ID not found");
        }
    }

    private List<Object> getValues(List<Field> fieldList, T entity) {
        List<Object> valueList = new ArrayList<>();
        for (var field : fieldList) {
            try {
                field.setAccessible(true);
                var value = field.get(entity);
                valueList.add(value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Field not found");
            }
        }
        return valueList;
    }

    private List<T> getEntityList(ResultSet rs) {

        List<T> entityList = new ArrayList<>();

        try {
            while (rs.next()) {

                entityList.add(this.getEntity(rs));

            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("SQL error: " + e.getMessage());
        }

        return entityList;
    }

    private T getEntity(ResultSet rs) throws SQLException {

        Object obj = null;
        Constructor<T> constructor = entityClassMetaData.getConstructor();
        try {
            obj = constructor.newInstance();

            List<Field> fieldList = entityClassMetaData.getAllFields();

            for (var field : fieldList) {
                try {
                    var fieldName = field.getName();
                    field.setAccessible(true);
                    field.set(obj, rs.getObject(fieldName));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Field not found");
                }
            }
            return (T) obj;

        } catch (InstantiationException | IllegalAccessException |
                InvocationTargetException e) {
            throw new RuntimeException("Object can't be created");
        }

    }
}
