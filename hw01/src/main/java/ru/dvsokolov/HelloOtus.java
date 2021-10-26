package ru.dvsokolov;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class HelloOtus {

    public static void main(String[] args) {
        List<Integer> testList = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            testList.add(i);
        }
        for (List<Integer> integers : Lists.partition(testList, 3)) {
            System.out.println(integers);
        }
    }
}
