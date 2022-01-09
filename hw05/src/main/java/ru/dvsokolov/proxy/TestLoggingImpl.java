package ru.dvsokolov.proxy;
import ru.dvsokolov.proxy.annotations.Log;

public class TestLoggingImpl implements TestLogging {

    public void calculation(int a) {
        System.out.println(a);
    }

    public void calculation(int a, int b) {
        System.out.println(a + b);
    }

    public void calculation(int a, int b, String c) {
        System.out.println(c + ":" + (a + b));
    }
}
