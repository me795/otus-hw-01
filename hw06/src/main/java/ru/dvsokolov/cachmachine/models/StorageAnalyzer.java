package ru.dvsokolov.cachmachine.models;

import ru.dvsokolov.cachmachine.exceptions.AmountRequestException;

import java.util.HashMap;
import java.util.Map;

public class StorageAnalyzer {

    public static Map<Integer,Integer> getSimpleAllocationNoteByCells (int[] denominations, Map<Integer,Integer> cellsBalance, long cashValue) throws AmountRequestException {

        Map<Integer,Integer>  simpleAllocationNoteMap = new HashMap<>();

        for (int denomination : denominations){
            int numberOfNotes = cellsBalance.get(denomination);
            int needNumberOfNotes = (int) (cashValue / denomination);
            if (needNumberOfNotes > 0){
                if (needNumberOfNotes > numberOfNotes){
                    cashValue = cashValue - (long) numberOfNotes * denomination;
                    simpleAllocationNoteMap.put(denomination, numberOfNotes);
                }else{
                    cashValue = cashValue - (long) needNumberOfNotes * denomination;
                    simpleAllocationNoteMap.put(denomination, needNumberOfNotes);
                }
            }
        }




        if (cashValue == 0){
            return simpleAllocationNoteMap;
        }else{
            throw new AmountRequestException("The amount requested cannot be issued");
        }
    }
}
