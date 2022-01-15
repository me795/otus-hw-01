package ru.dvsokolov.dataprocessor;

import ru.dvsokolov.model.Measurement;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {

        var resultMap = data.stream()
                .collect(toMap(Measurement::getName, Function.identity(), (a, b) -> new Measurement(a.getName(), a.getValue() + b.getValue())))
                .entrySet()
                .stream()
                .collect(toMap(
                        Map.Entry::getKey,
                        v -> v.getValue().getValue()
                ));

        return new TreeMap<>(resultMap);
    }
}
