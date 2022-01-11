package ru.dvsokolov.patterns.processor.homework;

import ru.dvsokolov.patterns.model.Message;
import ru.dvsokolov.patterns.processor.Processor;

import java.time.LocalDateTime;
import java.util.function.Consumer;

public class ProcessorWithException implements Processor {

    private final Consumer<Exception> errorHandler;

    public ProcessorWithException(Consumer<Exception> errorHandler) {
        this.errorHandler = errorHandler;
    }

    @Override
    public Message process(Message message) {

        var exceptionGenerator = new ExceptionGenerator(LocalDateTime::now);
        try {
            exceptionGenerator.throwEvenSecondException();
        } catch (Exception ex) {
            errorHandler.accept(ex);
        }
        return message;
    }

}
