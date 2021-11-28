package ru.dvsokolov.testfw;

import ru.dvsokolov.testfw.exceptions.ReflectionSlicerException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectionSlicer {

    public static Constructor<?> getDefaultConstructor(Class<?> clazz){
        Constructor<?>[] constructors = clazz.getConstructors();
        Constructor<?> defaultConstructor = null;
        for (Constructor<?> constructor : constructors) {
            Class<?>[] params = constructor.getParameterTypes();
            if (Arrays.stream(params).findAny().isPresent()) {
                continue;
            }
            defaultConstructor = constructor;
        }
        return defaultConstructor;
    }

    public static List<Method> getAllMethodsAnnotatedWith(Class<? extends Annotation> expectedAnnotation, Method[] inputMethods) throws ReflectionSlicerException {

//        List<Method> outputMethodList = new ArrayList<>();
//
//        for (Method method : inputMethods){
//            Annotation[] annotations = method.getDeclaredAnnotations();
//            for (Annotation annotation : annotations){
//                if (annotation.toString().contains(expectedAnnotation.getName())){
//                    outputMethodList.add(method);
//                }
//            }
//        }
//
//        return outputMethodList;

        return Arrays.stream(inputMethods)
                .filter(m -> m.isAnnotationPresent(expectedAnnotation))
                .collect(Collectors.toList());
    }
}
