package ru.dvsokolov.testfw;

public class Assertions {

    public static void checkEquals(int expected, int actual){
        if (expected != actual) {
            throw new AssertionError(String.format("Expected (%d), but actual value is (%d)", expected, actual));
        }
    }

    public static void checkEquals(long expected, long actual){
        if (expected != actual) {
            throw new AssertionError(String.format("Expected (%d), but actual value is (%d)", expected, actual));
        }
    }

    public static void checkEquals(String expected, String actual){
        if (!expected.equals(actual)) {
            throw new AssertionError(String.format("Expected (%s), but actual value is (%s)", expected, actual));
        }
    }
}
