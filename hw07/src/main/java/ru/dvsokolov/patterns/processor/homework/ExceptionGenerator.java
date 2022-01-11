package ru.dvsokolov.patterns.processor.homework;

import java.time.format.DateTimeFormatter;

public class ExceptionGenerator {

    private final DateTimeProvider dateTimeProvider;

    public ExceptionGenerator(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    public void throwEvenSecondException() throws EvenSecondException, InterruptedException {
        while (true){
            var seconds = dateTimeProvider.getDate().getSecond();
            if (seconds % 2 == 0){
                throw new EvenSecondException("EvenSecondException");
            }
            Thread.sleep(500);
        }
    }
}
