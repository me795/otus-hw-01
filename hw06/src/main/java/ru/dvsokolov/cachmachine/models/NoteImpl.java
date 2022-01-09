package ru.dvsokolov.cachmachine.models;

public class NoteImpl implements Note{

    private final int denomination;

    public NoteImpl(int denomination) {
        this.denomination = denomination;
    }

    @Override
    public int getDenomination() {
        return denomination;
    }
}
