package ru.dvsokolov.services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dvsokolov.lib.SensorDataBufferedWriter;
import ru.dvsokolov.api.SensorDataProcessor;
import ru.dvsokolov.api.model.SensorData;

import java.util.*;

// Этот класс нужно реализовать
public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;
    private final Queue<SensorData> dataBuffer = new LinkedList<>();
    private static final Comparator<SensorData> COMPARE_BY_TIME = Comparator.comparing(SensorData::getMeasurementTime);

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
    }

    @Override
    public void process(SensorData data) {
        var pushResult = dataBuffer.offer(data);
        if (!pushResult) {
            log.warn("Буфер переолнен");
        }
        if (dataBuffer.size() >= bufferSize) {
            flush();
        }
    }

    public void flush() {
        synchronized(dataBuffer) {
            if (dataBuffer.size() > 0) {
                var bufferedData = new ArrayList<>(dataBuffer);
                bufferedData.sort(SensorDataProcessorBuffered.COMPARE_BY_TIME);
                try {
                    writer.writeBufferedData(bufferedData);
                    dataBuffer.clear();
                } catch (Exception e) {
                    log.error("Ошибка в процессе записи буфера", e);
                }
            }
        }
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }
}
