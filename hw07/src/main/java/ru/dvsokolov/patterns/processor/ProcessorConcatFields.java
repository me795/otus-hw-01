package ru.dvsokolov.patterns.processor;

import ru.dvsokolov.patterns.model.Message;

public class ProcessorConcatFields implements ru.dvsokolov.patterns.processor.Processor {

    @Override
    public Message process(Message message) {
        var newFieldValue = String.join(" ", "concat:", message.getField1(), message.getField2(), message.getField3());
        return message.toBuilder().field4(newFieldValue).build();
    }
}
