package ru.dvsokolov.testfw;

import ru.dvsokolov.utils.IO;

import java.util.ArrayList;
import java.util.List;

public class ReportCollectorImpl implements ReportCollector {

    private final IO io;
    private final List<TestReport> testReportList = new ArrayList<>();

    public ReportCollectorImpl(IO io) {
        this.io = io;
    }

    public void addReport(TestReport testReport) {
        testReportList.add(testReport);
    }

    @Override
    public void viewReport() {

        int allTestCounter = 0;
        int passedTestCounter = 0;

        io.println();
        io.println("--------------------------------------------------------", IO.FontColor.ANSI_BLUE);
        io.println("Report: ", IO.FontColor.ANSI_BLUE);
        io.println();

        for (TestReport testReport : testReportList) {

            allTestCounter++;

            switch (testReport.getStatus().getCode()) {
                case "passed" -> {
                    passedTestCounter++;
                    io.print(testReport.getMethodName() + " ");
                    io.println(testReport.getStatus().getCode(), IO.FontColor.ANSI_GREEN);
                }
                case "failed" -> {
                    io.print(testReport.getMethodName() + " ");
                    io.println(testReport.getStatus().getCode(), IO.FontColor.ANSI_RED);
                    io.println(testReport.getComment(), IO.FontColor.ANSI_BLUE);
                }
                default -> io.println(testReport.getStatus().getCode(), IO.FontColor.ANSI_BLUE);
            }
        }
        io.println();
        io.println(String.format("Passed %d of %d tests", passedTestCounter, allTestCounter), IO.FontColor.ANSI_BLUE);
        io.println("--------------------------------------------------------", IO.FontColor.ANSI_BLUE);
    }
}
