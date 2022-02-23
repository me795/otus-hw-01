package ru.dvsokolov.ioc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.dvsokolov.ioc.appcontainer.AppComponentsContainerImpl;
import ru.dvsokolov.ioc.config.AppConfig;
import ru.dvsokolov.ioc.services.*;

import java.io.PrintStream;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {

    @DisplayName("Из контекста тремя способами должен корректно доставаться компонент с проставленными полями")
    @ParameterizedTest(name = "Достаем по: {0}")
    @CsvSource(value = {"GameProcessor, ru.dvsokolov.ioc.services.GameProcessor",
            "GameProcessorImpl, ru.dvsokolov.ioc.services.GameProcessor",
            "gameProcessor, ru.dvsokolov.ioc.services.GameProcessor",

            "IOService, ru.dvsokolov.ioc.services.IOService",
            "IOServiceConsole, ru.dvsokolov.ioc.services.IOService",
            "ioService, ru.dvsokolov.ioc.services.IOService",

            "PlayerService, ru.dvsokolov.ioc.services.PlayerService",
            "PlayerServiceImpl, ru.dvsokolov.ioc.services.PlayerService",
            "playerService, ru.dvsokolov.ioc.services.PlayerService",

            "EquationPreparer, ru.dvsokolov.ioc.services.EquationPreparer",
            "EquationPreparerImpl, ru.dvsokolov.ioc.services.EquationPreparer",
            "equationPreparer, ru.dvsokolov.ioc.services.EquationPreparer"
    })
    public void shouldExtractFromContextCorrectComponentWithNotNullFields(String classNameOrBeanId, Class<?> rootClass) throws Exception {
        var ctx = new AppComponentsContainerImpl(AppConfig.class);

        assertThat(classNameOrBeanId).isNotEmpty();
        Object component;
        if (classNameOrBeanId.charAt(0) == classNameOrBeanId.toUpperCase().charAt(0)) {
            Class<?> gameProcessorClass = Class.forName("ru.dvsokolov.ioc.services." + classNameOrBeanId);
            assertThat(rootClass).isAssignableFrom(gameProcessorClass);

            component = ctx.getAppComponent(gameProcessorClass);
        } else {
            component = ctx.getAppComponent(classNameOrBeanId);
        }
        assertThat(component).isNotNull();
        assertThat(rootClass).isAssignableFrom(component.getClass());

        var fields = Arrays.stream(component.getClass().getDeclaredFields())
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .peek(f -> f.setAccessible(true))
                .collect(Collectors.toList());

        for (var field: fields){
            var fieldValue = field.get(component);
            assertThat(fieldValue).isNotNull().isInstanceOfAny(IOService.class, PlayerService.class,
                    EquationPreparer.class, PrintStream.class, Scanner.class);
        }

    }
}