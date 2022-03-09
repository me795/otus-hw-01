package ru.dvsokolov.protobuf.service;
import java.util.List;


public interface RealDBService {
    List<Long> startCount(long start, long end);
}
