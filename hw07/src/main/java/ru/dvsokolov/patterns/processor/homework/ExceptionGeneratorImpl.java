package ru.dvsokolov.patterns.processor.homework;

import java.time.format.DateTimeFormatter;

public class ExceptionGeneratorImpl implements ExceptionGenerator {

    private final DateTimeProvider dateTimeProvider;

    public ExceptionGeneratorImpl(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    public void throwEvenSecondException() throws EvenSecondException {

        var seconds = dateTimeProvider.getDate().getSecond();
        if (seconds % 2 == 0) {
            throw new EvenSecondException("EvenSecondException");
        }

    }
}
