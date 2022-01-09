package ru.dvsokolov.cachmachine.models;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.dvsokolov.cachmachine.exceptions.AmountRequestException;
import ru.dvsokolov.cachmachine.exceptions.StorageOutOfRangeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@DisplayName("Класс AcceptorImplTest")
class CashMachineImplTest {

    private final int[] denominations = {5000, 2000, 1000, 500, 200, 100, 50};
    private CashMachine cashMachine;

    @BeforeEach
    void setUp() {
        List<Cell> cellList = new ArrayList<>();
        for (var denomination : denominations){
            Cell cell = new CellImpl(denomination);
            cellList.add(cell);
        }
        Storage storage = new StorageImpl(cellList, denominations);
        cashMachine = new CashMachineImpl(storage, denominations);
    }

    @DisplayName("Должен корректно добавлять все купюры в хранилище")
    @Test
    void putNotesTest(){
        Random random = new Random();
        List<Note> noteList = new ArrayList<>();
        long expectedAmount = 0;
        for (int i = 0; i < 100; i++){
            int index = random.nextInt(denominations.length);
            Note note = new NoteImpl(denominations[index]);
            noteList.add(note);
            expectedAmount += denominations[index];
        }
        cashMachine.putNotes(noteList);
        long actualAmount = cashMachine.getStorageBalance();

        System.out.println("expectedAmount " + expectedAmount);
        System.out.println("actualAmount " + actualAmount);

        Assertions.assertThat(actualAmount).isEqualTo(expectedAmount);
    }

    @DisplayName("Должен игнорировать купюры некорректного номинала")
    @Test
    void putNotesWithWrongDenominationTest(){
        Random random = new Random();
        List<Note> noteList = new ArrayList<>();
        long expectedAmount = 0;
        for (int i = 0; i < 100; i++){
            int index = random.nextInt(denominations.length);
            Note note = new NoteImpl(denominations[index]);
            noteList.add(note);
            expectedAmount += denominations[index];
        }
        Note note = new NoteImpl(3);
        noteList.add(note);

        cashMachine.putNotes(noteList);
        long actualAmount = cashMachine.getStorageBalance();

        System.out.println("expectedAmount " + expectedAmount);
        System.out.println("actualAmount " + actualAmount);

        Assertions.assertThat(actualAmount).isEqualTo(expectedAmount);
    }

    @DisplayName("Должен выдавать запрошенную сумму при наличии в хранилище купюр требуемого номинала")
    @Test
    void takeNotesByValueTest(){
        long expectedAmount = 7250;

        List<Note> inputNoteList = new ArrayList<>();
        Note inputNote;
        inputNote = new NoteImpl(5000);
        inputNoteList.add(inputNote);
        inputNote = new NoteImpl(2000);
        inputNoteList.add(inputNote);
        inputNote = new NoteImpl(1000);
        inputNoteList.add(inputNote);
        inputNote = new NoteImpl(1000);
        inputNoteList.add(inputNote);
        inputNote = new NoteImpl(200);
        inputNoteList.add(inputNote);
        inputNote = new NoteImpl(100);
        inputNoteList.add(inputNote);
        inputNote = new NoteImpl(100);
        inputNoteList.add(inputNote);
        inputNote = new NoteImpl(100);
        inputNoteList.add(inputNote);
        inputNote = new NoteImpl(50);
        inputNoteList.add(inputNote);

        cashMachine.putNotes(inputNoteList);

        List<Note> outputNoteList = null;
        try {
            outputNoteList = cashMachine.takeNotesByValue(expectedAmount);
        } catch (StorageOutOfRangeException | AmountRequestException e) {
            e.printStackTrace();
        }

        long actualAmount = 0;
        assert outputNoteList != null;
        for (var outputNote : outputNoteList){
            actualAmount += outputNote.getDenomination();
        }

        System.out.println("expectedAmount " + expectedAmount);
        System.out.println("actualAmount " + actualAmount);

        Assertions.assertThat(actualAmount).isEqualTo(expectedAmount);
    }

    @DisplayName("Должен выбрасывать исключение, если не может выдать запрошенную сумму из-за отсутствия в хранилище достаточного количества денег")
    @Test
    void takeNotesByValueExceptionTest1(){
        long expectedAmount = 9600;

        List<Note> inputNoteList = new ArrayList<>();
        Note inputNote;
        inputNote = new NoteImpl(5000);
        inputNoteList.add(inputNote);
        inputNote = new NoteImpl(2000);
        inputNoteList.add(inputNote);
        inputNote = new NoteImpl(1000);
        inputNoteList.add(inputNote);
        inputNote = new NoteImpl(1000);
        inputNoteList.add(inputNote);
        inputNote = new NoteImpl(200);
        inputNoteList.add(inputNote);
        inputNote = new NoteImpl(100);
        inputNoteList.add(inputNote);
        inputNote = new NoteImpl(100);
        inputNoteList.add(inputNote);
        inputNote = new NoteImpl(100);
        inputNoteList.add(inputNote);
        inputNote = new NoteImpl(50);
        inputNoteList.add(inputNote);

        cashMachine.putNotes(inputNoteList);

        Assertions.assertThatThrownBy(() -> cashMachine.takeNotesByValue(expectedAmount))
                .isInstanceOf(AmountRequestException.class);
    }


    @DisplayName("Должен выбрасывать исключение, если не может выдать запрошенную сумму из-за отсутствия в хранилище купюр нужного номинала")
    @Test
    void takeNotesByValueExceptionTest2(){
        long expectedAmount = 3600;

        List<Note> inputNoteList = new ArrayList<>();
        Note inputNote;
        inputNote = new NoteImpl(5000);
        inputNoteList.add(inputNote);
        inputNote = new NoteImpl(2000);
        inputNoteList.add(inputNote);
        inputNote = new NoteImpl(1000);
        inputNoteList.add(inputNote);
        inputNote = new NoteImpl(1000);
        inputNoteList.add(inputNote);
        inputNote = new NoteImpl(200);
        inputNoteList.add(inputNote);
        inputNote = new NoteImpl(100);
        inputNoteList.add(inputNote);
        inputNote = new NoteImpl(100);
        inputNoteList.add(inputNote);
        inputNote = new NoteImpl(100);
        inputNoteList.add(inputNote);
        inputNote = new NoteImpl(50);
        inputNoteList.add(inputNote);

        cashMachine.putNotes(inputNoteList);

        Assertions.assertThatThrownBy(() -> cashMachine.takeNotesByValue(expectedAmount))
                .isInstanceOf(AmountRequestException.class);
    }


}