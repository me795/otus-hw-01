package ru.dvsokolov.patterns.handler;

import ru.dvsokolov.patterns.listener.Listener;
import ru.dvsokolov.patterns.model.Message;

public interface Handler {
    Message handle(Message msg);

    void addListener(Listener listener);
    void removeListener(Listener listener);
}
