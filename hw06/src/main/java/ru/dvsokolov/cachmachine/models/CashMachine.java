package ru.dvsokolov.cachmachine.models;

import ru.dvsokolov.cachmachine.exceptions.AmountRequestException;
import ru.dvsokolov.cachmachine.exceptions.StorageOutOfRangeException;

import java.util.List;

public interface CashMachine {
    List<Note> takeNotesByValue(long cashValue) throws StorageOutOfRangeException, AmountRequestException;
    void putNotes(List<Note> noteList);
    long getStorageBalance();
}
