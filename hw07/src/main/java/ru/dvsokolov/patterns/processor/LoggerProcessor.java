package ru.dvsokolov.patterns.processor;
import ru.dvsokolov.patterns.model.Message;


public class LoggerProcessor implements ru.dvsokolov.patterns.processor.Processor {


    private final ru.dvsokolov.patterns.processor.Processor processor;

    public LoggerProcessor(ru.dvsokolov.patterns.processor.Processor processor) {
        this.processor = processor;
    }

    @Override
    public Message process(Message message) {
        System.out.println("log processing message:" + message);
        return processor.process(message);
    }
}
