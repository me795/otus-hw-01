package ru.dvsokolov.patterns.processor;

import ru.dvsokolov.patterns.model.Message;

public class ProcessorUpperField10 implements ru.dvsokolov.patterns.processor.Processor {

    @Override
    public Message process(Message message) {
        return message.toBuilder().field4(message.getField10().toUpperCase()).build();
    }
}
