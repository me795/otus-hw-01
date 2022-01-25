package ru.dvsokolov.myorm.jdbc.mapper;


import ru.dvsokolov.core.repository.DataTemplate;
import ru.dvsokolov.core.repository.executor.DbExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dvsokolov.crm.model.Client;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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
        var entity = dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id),
                rs -> {

                    try {
                        var entityField = entitySQLMetaData.getClass().getDeclaredField("entityClassMetaData");
                        entityField.setAccessible(true);
                        EntityClassMetaData entityClassMetaData = (EntityClassMetaData) entityField.get(entitySQLMetaData);

                        String name = entityClassMetaData.getName();
                        System.out.println(name);
                        Constructor<T> constructor = entityClassMetaData.getConstructor();
                        Object obj = constructor.newInstance();

                        try {
                            if (rs.next()) {

                                List<Field> fieldList = entityClassMetaData.getAllFields();

                                for (var field : fieldList){
                                    try {
                                        var fieldName = field.getName();
                                        field.setAccessible(true);
                                        field.set(obj,rs.getObject(fieldName));
                                    } catch (IllegalAccessException e) {
                                        throw new RuntimeException("Field not found");
                                    }
                                }
                                return obj;
                            }
                        } catch (SQLException e) {
                            log.error(e.getMessage(), e);
                            throw new RuntimeException("SQL error: "+ e.getMessage());
                        }

                    
                    } catch (NoSuchFieldException e) {
                        log.error(e.getMessage(), e);
                        throw new RuntimeException("Field not found");
                    } catch (IllegalAccessException e) {
                        log.error(e.getMessage(), e);
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        log.error(e.getMessage(), e);
                        throw new RuntimeException(e);
                    } catch (InstantiationException e) {
                        log.error(e.getMessage(), e);
                        throw new RuntimeException(e);
                    }

                    return null;
                });

        return (Optional<T>) entity;
    }

    @Override
    public List<T> findAll(Connection connection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long insert(Connection connection, T client) {
        var entityClassMetaData = getEntityClassMetaData(client);
        List<Field> fieldList = entityClassMetaData.getFieldsWithoutId();
        List<Object> valueList = new ArrayList<>();
        for (var field : fieldList){
            try {
                var fieldName = field.getName();
                field.setAccessible(true);
                var value = field.get(client);
                valueList.add(value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Field not found");
            }
        }

        String sql = entitySQLMetaData.getInsertSql();
        var clientId = dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), valueList);

        return clientId;
    }

    @Override
    public void update(Connection connection, T client) {
        throw new UnsupportedOperationException();
    }

    private EntityClassMetaData getEntityClassMetaData(T obj){
         return new EntityClassMetaDataImpl(obj.getClass());
    }
}
