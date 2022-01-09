package ru.dvsokolov.cachmachine.exceptions;

public class StorageOutOfRangeException extends Exception {

    public StorageOutOfRangeException() {}

    public StorageOutOfRangeException(String message)
    {
        super(message);
    }
}
