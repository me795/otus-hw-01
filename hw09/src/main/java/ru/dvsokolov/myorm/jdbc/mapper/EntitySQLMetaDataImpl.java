package ru.dvsokolov.myorm.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        List<Field> fields = entityClassMetaData.getAllFields();
        String fieldNames = fields.stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));
        return "SELECT " + fieldNames
                + " FROM "
                + entityClassMetaData.getName();
    }

    @Override
    public String getSelectByIdSql() {
        return this.getSelectAllSql() +
                " WHERE "
                + entityClassMetaData.getIdField().getName() + " = ?";
    }

    @Override
    public String getInsertSql() {

        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        String params = fields.stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));
        String values = fields.stream()
                .map(f -> "?")
                .collect(Collectors.joining(", "));

        return "INSERT INTO " + entityClassMetaData.getName()
                + " (" + params + ")"
                + " VALUES"
                + " (" + values + ")";
    }

    @Override
    public String getUpdateSql() {

        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        String params = fields.stream()
                .map(f -> f.getName() + " = ?")
                .collect(Collectors.joining(", "));


        return "UPDATE " + entityClassMetaData.getName()
                + " SET " + params
                + " WHERE "
                + entityClassMetaData.getIdField().getName() + " = ?";
    }

}
