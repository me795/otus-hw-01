package ru.dvsokolov.myorm.jdbc.mapper;

import ru.dvsokolov.core.annotations.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> entity;
    private String name;
    private Constructor<T> constructor;
    private List<Field> fieldList;


    private EntityClassMetaDataImpl(Class<T> entity) {
        this.entity = entity;
    }

    private EntityClassMetaDataImpl(Class<T> entity, String name, Constructor<T> constructor, List<Field> fieldList) {
        this.entity = entity;
        this.name = name;
        this.constructor = constructor;
        this.fieldList = fieldList;
    }

    public static <T> EntityClassMetaData<T> of (Class<T> entity) {
        var name = entity.getSimpleName();
        EntityClassMetaDataImpl<T> entityClassMetaData = null;
        try {
            var constructor =  entity.getConstructor();
            var fieldList = List.of(entity.getDeclaredFields());
            entityClassMetaData = new EntityClassMetaDataImpl(entity,name,constructor,fieldList);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Constructor not found in " + entity.getName());
        }

        return entityClassMetaData;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Constructor<T> getConstructor() {
        return this.constructor;
    }

    @Override
    public Field getIdField() {
        return this.fieldList.stream()
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("id field not found"));
    }

    @Override
    public List<Field> getAllFields() {
        return this.fieldList;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return this.fieldList.stream()
                .filter(f -> ! f.isAnnotationPresent(Id.class))
                .collect(Collectors.toList());
    }

}
