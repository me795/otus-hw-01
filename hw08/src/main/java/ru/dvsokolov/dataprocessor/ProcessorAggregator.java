package ru.dvsokolov.dataprocessor;

import ru.dvsokolov.model.Measurement;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {

        return data.stream()
                .collect(groupingBy(
                        Measurement::getName,
                        TreeMap::new,
                        Collectors.summingDouble(Measurement::getValue)
                        ));

    }
}
