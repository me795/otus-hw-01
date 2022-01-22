package ru.dvsokolov.patterns.listener.homework;

import ru.dvsokolov.patterns.listener.Listener;
import ru.dvsokolov.patterns.model.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, Message> messageMap = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        messageMap.put(msg.getId(), msg.copy());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(messageMap.get(id));
    }


}
