package ru.dvsokolov.api;


import ru.dvsokolov.api.model.SensorData;

public interface SensorDataProcessor {
    void process(SensorData data);

    default void onProcessingEnd() {
    }
}
