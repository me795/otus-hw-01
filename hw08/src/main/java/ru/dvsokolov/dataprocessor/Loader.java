package ru.dvsokolov.dataprocessor;

import ru.dvsokolov.model.Measurement;

import java.io.IOException;
import java.util.List;

public interface Loader {

    List<Measurement> load() throws IOException;
}
