package ru.dvsokolov.proxy;

import ru.dvsokolov.proxy.annotations.Log;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class Ioc {

    private Ioc() {
    }

    static TestLogging createTestLogging() {
        InvocationHandler handler = new InvocationHandlerImpl(new TestLoggingImpl());
        return (TestLogging) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLogging.class}, handler);
    }

    static class InvocationHandlerImpl implements InvocationHandler {
        private final TestLogging testLogging;

        InvocationHandlerImpl(TestLoggingImpl testLogging) {
            this.testLogging = testLogging;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if (method.isAnnotationPresent(Log.class)) {
                System.out.println("----------------------------------");
                System.out.println("invoking method: " + method.getName() + ", params(" + args.length + "): ");
                for (int i = 0; i < args.length; i++) {
                    System.out.println(" arg " + i + ": " + args[i]);
                }
                System.out.println("----------------------------------");
            }
            return method.invoke(testLogging, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "myClass=" + testLogging +
                    '}';
        }
    }
}
