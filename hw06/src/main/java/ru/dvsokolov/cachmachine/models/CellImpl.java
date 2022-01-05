package ru.dvsokolov.cachmachine.models;

import ru.dvsokolov.cachmachine.exceptions.IncorrectNoteException;
import ru.dvsokolov.cachmachine.exceptions.StorageOutOfRangeException;

import java.util.ArrayList;
import java.util.List;

public class CellImpl implements Cell {

    private final int denomination;
    private int noteCounter;

    public CellImpl(int denomination) {
        this.denomination = denomination;
    }

    @Override
    public int getDenomination() {
        return denomination;
    }

    @Override
    public void putNote(Note note) throws IncorrectNoteException {
        if (note.getDenomination() == denomination) {
            noteCounter++;
        } else {
            throw new IncorrectNoteException("Incorrect denomination of banknote " + note.getDenomination() + ". Expected denomination is  " + denomination);
        }
    }

    @Override
    public void putSomeNotes(List<Note> noteList) throws IncorrectNoteException {
        for (Note note : noteList) {
            putNote(note);
        }
    }

    @Override
    public Note takeNote() throws StorageOutOfRangeException {
        if (noteCounter > 0){
            noteCounter--;
            return new NoteImpl(denomination);
        }else{
            throw new StorageOutOfRangeException("Cell with denomination " + denomination + " is out of range");
        }
    }

    @Override
    public List<Note> takeSomeNotesList(int count) throws StorageOutOfRangeException {
        List<Note> noteList = new ArrayList<>();
        for (int i = 0; i < count; i++){
            var note = takeNote();
            noteList.add(note);
        }
        return noteList;
    }

    @Override
    public long getBalance() {
        return (long) noteCounter * denomination;
    }

    @Override
    public int getNumberOfNotesInCell() {
        return noteCounter;
    }
}
