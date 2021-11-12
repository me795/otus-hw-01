package ru.dvsokolov.testfw;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class TestInstanceImpl implements TestInstance {

    private final Set<Method> beforeMethods;
    private final Method testMethod;
    private final Set<Method> afterMethods;
    private final Constructor<?> defaultConstructor;
    private final TestReport testReport = new TestReportImpl();

    public TestInstanceImpl(Constructor<?> defaultConstructor, Set<Method> beforeMethods, Method testMethod, Set<Method> afterMethods) {
        this.defaultConstructor = defaultConstructor;
        this.beforeMethods = beforeMethods;
        this.testMethod = testMethod;
        this.afterMethods = afterMethods;
        testReport.setStatus(TestReport.Status.UNDEFINED);
    }

    @Override
    public void test() {

        Object object = getObject();
        runExtraMethods(beforeMethods, object);

        try {
            testReport.setMethodName(testMethod.getName());
            testMethod.invoke(object);
            testReport.setStatus(TestReport.Status.PASSED);
        }  catch (IllegalAccessException | InvocationTargetException e) {
            testReport.setComment(e.getCause().getMessage());
            testReport.setStatus(TestReport.Status.FAILED);
        }

        runExtraMethods(afterMethods, object);
    }


    private Object getObject() {
        assert defaultConstructor != null;
        Object object = null;
        try {
            object = defaultConstructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return object;
    }

    private void runExtraMethods(Set<Method> methods, Object object) {

        for (Method method : methods) {
            try {
                method.invoke(object);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public TestReport getReport() {
        return testReport;
    }
}
