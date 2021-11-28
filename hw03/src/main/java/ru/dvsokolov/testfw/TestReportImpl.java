package ru.dvsokolov.testfw;

public class TestReportImpl implements TestReport {

    private String comment;
    private String methodName;
    private Status status;


    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public void setMethodName(String methodName) {
       this.methodName = methodName;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public String getMethodName() {
        return methodName;
    }

}
