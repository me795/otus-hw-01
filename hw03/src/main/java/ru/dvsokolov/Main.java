package ru.dvsokolov;

import ru.dvsokolov.experiment.test.ExperimentTest;
import ru.dvsokolov.testfw.*;
import ru.dvsokolov.utils.IO;
import ru.dvsokolov.utils.IOImpl;

public class Main {

    public static void main(String[] args) {

        Class<?> testClass = ExperimentTest.class;
        IO io = new IOImpl(System.out,System.in);
        ReportCollector reportCollector = new ReportCollectorImpl(io);

        TestRunner testRunner = new TestRunnerImpl(testClass,reportCollector);
        testRunner.launch();
        reportCollector.viewReport();
    }
}
