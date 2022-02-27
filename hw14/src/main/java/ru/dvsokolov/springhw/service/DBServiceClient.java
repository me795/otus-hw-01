package ru.dvsokolov.springhw.service;

import ru.dvsokolov.springhw.model.Client;

import java.util.List;
import java.util.Optional;

public interface DBServiceClient {

    Client saveClient(Client client);

    void deleteClient(long clientId);

    Optional<Client> getClient(long id);

    List<Client> findAll();
}
