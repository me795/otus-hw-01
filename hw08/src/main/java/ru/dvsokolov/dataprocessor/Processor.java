package ru.dvsokolov.dataprocessor;

import ru.dvsokolov.model.Measurement;

import java.util.List;
import java.util.Map;

public interface Processor {

    Map<String, Double> process(List<Measurement> data);
}
