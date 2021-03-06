package ru.dvsokolov.testfw;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class TestInstanceImpl implements TestInstance {

    private final List<Method> beforeMethods;
    private final Method testMethod;
    private final List<Method> afterMethods;
    private final Constructor<?> defaultConstructor;

    public TestInstanceImpl(Constructor<?> defaultConstructor, List<Method> beforeMethods, Method testMethod, List<Method> afterMethods) {
        this.defaultConstructor = defaultConstructor;
        this.beforeMethods = beforeMethods;
        this.testMethod = testMethod;
        this.afterMethods = afterMethods;
    }

    @Override
    public TestReport executeTest() {

        TestReport testReport = new TestReportImpl();
        testReport.setStatus(TestReport.Status.UNDEFINED);

        try {
            Object object = getObject();

            try {
                runExtraMethods(beforeMethods, object);
                try {
                    testReport.setMethodName(testMethod.getName());
                    testMethod.invoke(object);
                    testReport.setStatus(TestReport.Status.PASSED);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    testReport.setComment(e.getCause().getMessage());
                    testReport.setStatus(TestReport.Status.FAILED);
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            } finally {
                try {
                    runExtraMethods(afterMethods, object);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return testReport;
    }


    private Object getObject() throws InstantiationException, IllegalAccessException, InvocationTargetException {

        assert defaultConstructor != null;
        return defaultConstructor.newInstance();
    }

    private void runExtraMethods(List<Method> methods, Object object) throws IllegalAccessException, InvocationTargetException {

        for (Method method : methods) {
            method.invoke(object);
        }
    }

}
