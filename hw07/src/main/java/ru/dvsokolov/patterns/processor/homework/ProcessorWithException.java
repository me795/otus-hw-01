package ru.dvsokolov.patterns.processor.homework;

import ru.dvsokolov.patterns.model.Message;
import ru.dvsokolov.patterns.processor.Processor;

import java.time.LocalDateTime;
import java.util.function.Consumer;

public class ProcessorWithException implements Processor {

    private final Consumer<Exception> errorHandler;
    private final ExceptionGenerator exceptionGenerator;

    public ProcessorWithException(Consumer<Exception> errorHandler, ExceptionGenerator exceptionGenerator) {
        this.errorHandler = errorHandler;
        this.exceptionGenerator = exceptionGenerator;
    }

    @Override
    public Message process(Message message) {

        try {
            exceptionGenerator.throwEvenSecondException();
        } catch (Exception ex) {
            errorHandler.accept(ex);
        }
        return message;
    }

}
