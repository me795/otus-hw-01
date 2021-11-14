package ru.dvsokolov.testfw;

import ru.dvsokolov.testfw.annotations.After;
import ru.dvsokolov.testfw.annotations.Before;
import ru.dvsokolov.testfw.annotations.Test;
import ru.dvsokolov.testfw.exceptions.ReflectionSlicerException;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TestRunnerImpl implements TestRunner {

    private final Class<?> testClass;
    private final ReportCollector reportCollector;

    public TestRunnerImpl(Class<?> testClass, ReportCollector reportCollector) {
        this.testClass = testClass;
        this.reportCollector = reportCollector;
    }

    @Override
    public void launch() {

        List<TestInstance> testInstanceList = prepareInstances(testClass);

        for (TestInstance testInstance : testInstanceList) {
            reportCollector.addReport(testInstance.executeTest());
        }
    }

    private List<TestInstance> prepareInstances(Class<?> testClass) {

        List<TestInstance> testInstanceList = new ArrayList<>();

        try {
            Set<Method> beforeMethods = ReflectionSlicer.getAllMethodsAnnotatedWith(Before.class);
            Set<Method> testMethods = ReflectionSlicer.getAllMethodsAnnotatedWith(Test.class);
            Set<Method> afterMethods = ReflectionSlicer.getAllMethodsAnnotatedWith(After.class);
            Constructor<?> defaultConstructor = ReflectionSlicer.getDefaultConstructor(testClass);

            for (Method testMethod : testMethods) {
                TestInstance testInstance = new TestInstanceImpl(defaultConstructor, beforeMethods, testMethod, afterMethods);
                testInstanceList.add(testInstance);
            }

        } catch (ReflectionSlicerException e) {
            e.printStackTrace();
        }

        return testInstanceList;
    }


}
