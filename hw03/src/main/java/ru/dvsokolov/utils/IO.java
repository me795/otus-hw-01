package ru.dvsokolov.utils;

public interface IO {

    enum FontColor {

        ANSI_RESET("\u001B[0m"),
        ANSI_BLUE("\u001B[34m"),
        ANSI_RED("\u001B[31m"),
        ANSI_GREEN("\u001B[32m"),
        ANSI_YELLOW("\u001B[33m"),
        ANSI_PURPLE("\u001B[35m"),
        ANSI_CYAN("\u001B[36m");

        private String code;

        FontColor(String code){
            this.code = code;
        }
        public String getCode(){ return code;}
    }

    void println();
    void println(String text);
    void println(String text, FontColor fontColor);
    void print(String text);
    void print(String text, FontColor fontColor);
    int inputInt();
    long inputLong();
    String inputString();
    
}