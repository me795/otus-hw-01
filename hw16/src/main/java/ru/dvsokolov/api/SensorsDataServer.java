package ru.dvsokolov.api;

import ru.dvsokolov.api.model.SensorData;

public interface SensorsDataServer {
    void onReceive(SensorData sensorData);
}
