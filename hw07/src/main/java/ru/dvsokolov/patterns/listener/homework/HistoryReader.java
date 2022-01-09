package ru.dvsokolov.patterns.listener.homework;

import ru.dvsokolov.patterns.model.Message;

import java.util.Optional;

public interface HistoryReader {

    Optional<Message> findMessageById(long id);
}
