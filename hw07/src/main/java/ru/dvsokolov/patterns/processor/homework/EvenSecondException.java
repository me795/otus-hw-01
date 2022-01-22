package ru.dvsokolov.patterns.processor.homework;

import java.time.LocalDateTime;

public class EvenSecondException extends Exception {

    public EvenSecondException() {}

    public EvenSecondException(String message)
    {
        super(message);
    }

}
