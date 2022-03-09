package ru.dvsokolov.protobuf.service;

import java.util.ArrayList;
import java.util.List;

public class RealDBServiceImpl implements RealDBService {
    private final List<Long> longList;

    public RealDBServiceImpl() {
        longList = new ArrayList<>();
    }

    @Override
    public List<Long> startCount(long start, long end) {

        long counter = start;
        while (counter <= end){
            longList.add(counter);
            counter++;
        }

        return longList;
    }

}
