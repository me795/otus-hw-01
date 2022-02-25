package ru.dvsokolov.ioc.appcontainer;

import ru.dvsokolov.ioc.appcontainer.api.AppComponent;
import ru.dvsokolov.ioc.appcontainer.api.AppComponentsContainer;
import ru.dvsokolov.ioc.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

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
                .sorted(Comparator.comparing(m -> m.getAnnotation(AppComponent.class).order()))
                .forEach(m -> {
                    var methodParams = Arrays.stream(m.getParameterTypes())
                            .map(this::getAppComponent).toArray();
                    try {
                        var component = m.invoke(configClass.getDeclaredConstructor().newInstance(), methodParams);
                        var key = m.getAnnotation(AppComponent.class).name();
                        if (! appComponentsByName.containsKey(key)) {
                            appComponents.add(component);
                            appComponentsByName.put(key, component);
                        }else{
                            throw new RuntimeException("Duplication of component names: " + key);
                        }
                    } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
                        e.printStackTrace();
                        throw new RuntimeException("Invoke failed for method: " + m.getName());
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

        return (C) appComponents.stream()
                .filter(cmp -> componentClass.isAssignableFrom(cmp.getClass()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No such component"));

    }

    @Override
    public <C> C getAppComponent(String componentName) {
        C resultComponent = (C) appComponentsByName.get(componentName);
        if (resultComponent == null){
            throw new RuntimeException("No such component");
        }else {
            return (C) appComponentsByName.get(componentName);
        }
    }
}
