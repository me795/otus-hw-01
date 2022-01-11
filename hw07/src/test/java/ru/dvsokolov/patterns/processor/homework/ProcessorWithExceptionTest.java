package ru.dvsokolov.patterns.processor.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.dvsokolov.patterns.model.Message;
import ru.dvsokolov.patterns.processor.Processor;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;

class ProcessorWithExceptionTest {


    @Test
    @DisplayName("Должно выбрасывать исключение в чётную секунду")
    void processorExceptionTest() {

        var message = mock(Message.class);
        Processor processor = new ProcessorWithException(ex -> {
            int seconds = LocalDateTime.now().getSecond();
            int mod = seconds % 2;
            throw new TestException(String.valueOf(mod));
        });

        assertThatExceptionOfType(TestException.class).isThrownBy(() -> processor.process(message))
                .withMessage("0");

    }


    private static class TestException extends RuntimeException {
        public TestException(String message) {
            super(message);
        }
    }
}