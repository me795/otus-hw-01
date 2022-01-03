package ru.dvsokolov.proxy;

public class Main {
    public static void main(String[] args) {
        TestLogging testLogging = Ioc.createTestLogging();
        testLogging.calculation(1);
        testLogging.calculation(1,2);
        testLogging.calculation(3,4,"test");

    }
}
