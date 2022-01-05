package ru.dvsokolov.cachmachine.models;

import ru.dvsokolov.cachmachine.exceptions.AmountRequestException;
import ru.dvsokolov.cachmachine.exceptions.IncorrectNoteException;
import ru.dvsokolov.cachmachine.exceptions.StorageOutOfRangeException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CashMachineImpl implements CashMachine {

    private final Storage storage;
    private final int[] denominatios;

    public CashMachineImpl(Storage storage, int[] denominatios) {
        this.storage = storage;
        this.denominatios = denominatios;
    }

    @Override
    public List<Note> takeNotesByValue(long cashValue) throws StorageOutOfRangeException, AmountRequestException {

        return storage.takeNotesByValue(cashValue);
    }

    @Override
    public void putNotes(List<Note> noteList) {
        for (int denomination : denominatios) {
            var filteredNoteListByDenominate = noteList.stream()
                    .filter(val -> val.getDenomination() == denomination)
                    .collect(Collectors.toList());

            try {
                storage.putNotes(denomination, filteredNoteListByDenominate);
            } catch (IncorrectNoteException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public long getStorageBalance() {
        return storage.getBalance();
    }
}
