package ru.dvsokolov.testfw;

import ru.dvsokolov.testfw.annotations.After;
import ru.dvsokolov.testfw.annotations.Before;
import ru.dvsokolov.testfw.annotations.Test;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TestRunnerImpl implements TestRunner{

    private final Class<? extends ObservableClass> testClass;
    private final ReportCollector reportCollector;
    private final List<TestInstance> testInstanceList = new ArrayList<>();

    public TestRunnerImpl(Class<? extends ObservableClass> testClass, ReportCollector reportCollector) {
        this.testClass = testClass;
        this.reportCollector = reportCollector;

        try {
            Set<Method> beforeMethods = ReflectionSlicer.getAllMethodsAnnotatedWith(Before.class);
            Set<Method> testMethods = ReflectionSlicer.getAllMethodsAnnotatedWith(Test.class);
            Set<Method> afterMethods = ReflectionSlicer.getAllMethodsAnnotatedWith(After.class);
            Constructor<?> defaultConstructor = ReflectionSlicer.getDefaultConstructor(testClass);

            for (Method testMethod : testMethods) {
                TestInstance testInstance = new TestInstanceImpl(defaultConstructor,beforeMethods,testMethod,afterMethods);
                testInstanceList.add(testInstance);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void launch() {

        for (TestInstance testInstance : testInstanceList){
            testInstance.test();
            reportCollector.addReport(testInstance.getReport());
        }

    }


}
