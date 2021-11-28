package ru.dvsokolov.testfw;

public interface TestReport {

    enum Status {

        PASSED("passed"),
        FAILED("failed"),
        SKIPPED("skipped"),
        UNDEFINED("undefined");

        private String code;

        Status(String code){
            this.code = code;
        }
        public String getCode(){ return code;}
    }

    void setComment(String comment);
    void setStatus(Status status);
    void setMethodName(String methodName);
    String getComment();
    Status getStatus();
    String getMethodName();
}
