package ru.dvsokolov.myorm.jdbc.mapper;

import ru.dvsokolov.core.annotations.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl implements EntityClassMetaData {

    private final Class entity;

    public EntityClassMetaDataImpl(Class entity) {
        this.entity = entity;
    }

    @Override
    public String getName() {
        return entity.getSimpleName();
    }

    @Override
    public Constructor getConstructor() {
        try {
            return entity.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Constructor not found in " + entity.getName());
        }
    }

    @Override
    public Field getIdField() {

        var fields = this.getAllFields();

        return fields.stream()
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .get();
    }

    @Override
    public List<Field> getAllFields() {
        return List.of(entity.getDeclaredFields());
    }

    @Override
    public List<Field> getFieldsWithoutId() {

        var fields = this.getAllFields();

        return fields.stream()
                .filter(f -> ! f.isAnnotationPresent(Id.class))
                .collect(Collectors.toList());
    }

}
