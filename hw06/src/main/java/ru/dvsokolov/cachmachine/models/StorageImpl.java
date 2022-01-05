package ru.dvsokolov.cachmachine.models;

import ru.dvsokolov.cachmachine.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StorageImpl implements Storage{

    private final List<Cell> cellList;
    private final int[] denominations;

    public StorageImpl(List<Cell> cellList, int[] denominations) {
        this.cellList = cellList;
        this.denominations = denominations;
    }

    @Override
    public long getBalance() {
        return cellList.stream()
                .mapToLong(Cell::getBalance)
                .reduce(0,Long::sum);
    }

    @Override
    public List<Note> takeNotesByValue(long cashValue) throws AmountRequestException, StorageOutOfRangeException {

        List<Note> noteList = new ArrayList<>();

        var cellsBalance = cellList.stream()
                .collect(Collectors.toMap(k -> k.getDenomination(), v -> v.getNumberOfNotesInCell()));



        var simpleAllocationNoteMap = StorageAnalyzer.getSimpleAllocationNoteByCells(denominations,cellsBalance,cashValue);

        for (var cell : cellList){
            var numberOfNotes = simpleAllocationNoteMap.get(cell.getDenomination());
            if (numberOfNotes != null) {
                var partOfNotesList = cell.takeSomeNotesList(numberOfNotes);
                noteList.addAll(partOfNotesList);
            }
        }

        return noteList;
    }

    @Override
    public void putNotes(int denomination, List<Note> noteList) throws IncorrectNoteException {

        var filteredCells = cellList.stream()
                .filter( val -> val.getDenomination() == denomination)
                .collect(Collectors.toList());
        if (filteredCells.size() == 1) {
            var cell = filteredCells.get(0);
            cell.putSomeNotes(noteList);
        }
    }
}
