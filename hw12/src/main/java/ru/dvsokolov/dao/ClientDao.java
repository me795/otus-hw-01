package ru.dvsokolov.dao;

import ru.dvsokolov.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientDao {

    Client saveClient(Client client);

    Optional<Client> getClient(long id);

    List<Client> findAll();

}
