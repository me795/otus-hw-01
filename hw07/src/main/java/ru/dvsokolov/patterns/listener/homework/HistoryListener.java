package ru.dvsokolov.patterns.listener.homework;

import ru.dvsokolov.patterns.listener.Listener;
import ru.dvsokolov.patterns.model.Message;
import java.util.Optional;


public class HistoryListener implements Listener, ru.dvsokolov.patterns.listener.homework.HistoryReader {

    @Override
    public void onUpdated(Message msg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        throw new UnsupportedOperationException();
    }
}
