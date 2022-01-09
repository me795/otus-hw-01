package ru.dvsokolov.patterns;

import ru.dvsokolov.patterns.handler.ComplexProcessor;
import ru.dvsokolov.patterns.listener.ListenerPrinterConsole;
import ru.dvsokolov.patterns.model.Message;
import ru.dvsokolov.patterns.processor.LoggerProcessor;
import ru.dvsokolov.patterns.processor.ProcessorConcatFields;
import ru.dvsokolov.patterns.processor.ProcessorUpperField10;

import java.util.List;

public class Demo {
    public static void main(String[] args) {
        var processors = List.of(new ProcessorConcatFields(),
                new LoggerProcessor(new ProcessorUpperField10()));

        var complexProcessor = new ComplexProcessor(processors, ex -> {});
        var listenerPrinter = new ListenerPrinterConsole();
        complexProcessor.addListener(listenerPrinter);

        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        complexProcessor.removeListener(listenerPrinter);
    }
}
