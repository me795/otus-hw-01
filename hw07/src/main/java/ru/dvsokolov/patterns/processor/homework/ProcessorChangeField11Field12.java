package ru.dvsokolov.patterns.processor.homework;

import ru.dvsokolov.patterns.model.Message;
import ru.dvsokolov.patterns.processor.Processor;

public class ProcessorChangeField11Field12 implements Processor {

    @Override
    public Message process(Message message) {
        var newField11Value = message.getField12();
        var newField12Value = message.getField11();
        return message.toBuilder().field11(newField11Value).field12(newField12Value).build();
    }
}
