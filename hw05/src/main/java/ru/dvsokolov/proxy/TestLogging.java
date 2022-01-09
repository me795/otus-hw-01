package ru.dvsokolov.proxy;

import ru.dvsokolov.proxy.annotations.Log;

public interface TestLogging {
    @Log
    void calculation(int a);
    void calculation(int a, int b);
    @Log
    void calculation(int a, int b, String c);
}
