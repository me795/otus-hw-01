package ru.dvsokolov.cachmachine.models;
import ru.dvsokolov.cachmachine.exceptions.AmountRequestException;
import ru.dvsokolov.cachmachine.exceptions.IncorrectNoteException;
import ru.dvsokolov.cachmachine.exceptions.StorageOutOfRangeException;

import java.util.List;

public interface Storage {
    long getBalance();
    List<Note> takeNotesByValue(long cashValue) throws AmountRequestException, StorageOutOfRangeException;
    void putNotes(int denomination, List<Note> noteList) throws IncorrectNoteException;
}
