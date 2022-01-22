package ru.dvsokolov.patterns.processor.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.dvsokolov.patterns.model.Message;
import ru.dvsokolov.patterns.processor.Processor;

import java.time.LocalDateTime;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProcessorWithExceptionTest {


    @Test
    @DisplayName("Должно выбрасывать исключение в чётную секунду")
    void processorExceptionTest() {

        var message = mock(Message.class);
        var dateTimeProvider = mock(DateTimeProvider.class);

        var localDateTime = LocalDateTime.now();
        Random rand = new Random();
        int rndEventSecond = rand.nextInt(29) *2;
        var testDateTime = localDateTime.withSecond(rndEventSecond);
        when(dateTimeProvider.getDate()).thenReturn(testDateTime);

        Processor processor = new ProcessorWithException(ex -> {
            throw new TestException(ex.getMessage());
        }, new ExceptionGeneratorImpl(dateTimeProvider));

        assertThatExceptionOfType(TestException.class).isThrownBy(() -> processor.process(message));

    }

    @Test
    @DisplayName("Не должно выбрасывать исключение в нечётную секунду")
    void processorWithoutExceptionTest() {

        var message = mock(Message.class);
        var dateTimeProvider = mock(DateTimeProvider.class);

        var localDateTime = LocalDateTime.now();
        Random rand = new Random();
        int rndOddSecond = rand.nextInt(29) * 2 + 1;
        var testDateTime = localDateTime.withSecond(rndOddSecond);
        when(dateTimeProvider.getDate()).thenReturn(testDateTime);

        Processor processor = new ProcessorWithException(ex -> {
            throw new TestException(ex.getMessage());
        }, new ExceptionGeneratorImpl(dateTimeProvider));

        assertThatCode(() -> processor.process(message)).doesNotThrowAnyException();
    }


    private static class TestException extends RuntimeException {
        public TestException(String message) {
            super(message);
        }
    }
}