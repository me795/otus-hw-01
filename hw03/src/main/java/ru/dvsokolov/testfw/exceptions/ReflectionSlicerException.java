package ru.dvsokolov.testfw.exceptions;

public class ReflectionSlicerException extends Exception {

    @Override
    public String toString()
    {
        return "Error. Bad result from class scanner (reflection library).";
    }
}
