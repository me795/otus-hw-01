package ru.dvsokolov.cachmachine.exceptions;

public class IncorrectNoteException extends Exception {

    public IncorrectNoteException() {}

    public IncorrectNoteException(String message)
    {
        super(message);
    }
}
