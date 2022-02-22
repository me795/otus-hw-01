package ru.dvsokolov.ioc.appcontainer;

import ru.dvsokolov.ioc.appcontainer.api.AppComponent;
import ru.dvsokolov.ioc.appcontainer.api.AppComponentsContainer;
import ru.dvsokolov.ioc.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);

        var methods = configClass.getDeclaredMethods();
        Arrays.stream(methods)
                .filter(m -> m.isAnnotationPresent(AppComponent.class))
                .sorted((m1, m2) -> Integer.compare(m1.getAnnotation(AppComponent.class).order(), m2.getAnnotation(AppComponent.class).order()))
                .forEach(m -> {
                    var params = m.getParameters();
                    var paramsReady = Arrays.stream(params)
                            .map(p ->
                                    getAppComponent(p.getType())
                            ).toArray();
                    try {
                        var component = m.invoke(configClass.getDeclaredConstructor().newInstance(), paramsReady);
                        appComponents.add(component);
                        appComponentsByName.put(m.getAnnotation(AppComponent.class).name(), component);
                    } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {

        var interfaceComponentClass = (componentClass.isInterface())
                ? componentClass
                : Arrays.stream(componentClass.getInterfaces())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Interfaces not found"));

        return (C) appComponents.stream()
                .filter(cmp -> Arrays.stream(cmp.getClass().getInterfaces())
                        .findFirst().orElseThrow(() -> new RuntimeException("Interfaces not found"))
                        .getName().equals(interfaceComponentClass.getName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No such component"));

    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}
