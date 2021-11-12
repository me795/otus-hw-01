package ru.dvsokolov.utils;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IOImpl implements IO {

    private final PrintStream out;
    private final Scanner in;

    public IOImpl(PrintStream out, InputStream in) {
        this.out = out;
        this.in = new Scanner(in);
    }

    @Override
    public void println(){
        out.println();
    }

    @Override
    public void println(String text){

        print(text);
        out.println();

    }

    @Override
    public void println(String text, FontColor fontColor){

        print(text,fontColor);
        out.println();

    }

    @Override
    public void print(String text){

        out.print(text);

    }

    @Override
    public void print(String text, FontColor fontColor){

        FontColor fontColorReset = FontColor.ANSI_RESET;
        out.print(fontColor.getCode() + text + fontColorReset.getCode());

    }

    @Override
    public int inputInt() {

        long userInput = inputLong();
        return (int)userInput;

    }

    @Override
    public long inputLong() {

        String userInput;

        boolean is_digital = false;
        do {
            userInput = inputString();
            if (userInput.matches("[-+]?\\d+")) {
                is_digital = true;
            }else {
                out.println(FontColor.ANSI_RED.getCode() + "Пожалуйста, введите число" + FontColor.ANSI_RESET.getCode());
            }
        } while (!is_digital);

        return Long.parseLong(userInput);
    }

    @Override
    public String inputString() {

        return in.nextLine().trim();
    }
    
}