package ru.dvsokolov.cachmachine.exceptions;

public class AmountRequestException extends Exception {

    public AmountRequestException() {}

    public AmountRequestException(String message)
    {
        super(message);
    }
}
