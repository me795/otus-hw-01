package ru.dvsokolov.testfw;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import ru.dvsokolov.testfw.exceptions.ReflectionSlicerException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

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

    public static Set<Method> getAllMethodsAnnotatedWith(Class<? extends Annotation> annotation) throws ReflectionSlicerException {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forJavaClassPath())
                .setScanners(new MethodAnnotationsScanner()));
        return reflections.getMethodsAnnotatedWith(annotation);

    }
}
