package ru.dvsokolov.cachmachine.models;
import ru.dvsokolov.cachmachine.exceptions.IncorrectNoteException;
import ru.dvsokolov.cachmachine.exceptions.StorageOutOfRangeException;

import java.util.List;

public interface Cell {
    int getDenomination();
    void putNote(Note note) throws IncorrectNoteException;
    void putSomeNotes(List<Note> noteList) throws IncorrectNoteException;
    Note takeNote() throws StorageOutOfRangeException;
    List<Note> takeSomeNotesList(int count) throws StorageOutOfRangeException;
    long getBalance();
    int getNumberOfNotesInCell();
}
