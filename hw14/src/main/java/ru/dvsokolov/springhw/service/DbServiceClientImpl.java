package ru.dvsokolov.springhw.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.dvsokolov.springhw.model.Client;
import ru.dvsokolov.springhw.repository.AddressRepository;
import ru.dvsokolov.springhw.repository.ClientRepository;
import ru.dvsokolov.springhw.repository.PhoneRepository;
import ru.dvsokolov.springhw.sessionmanager.TransactionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final TransactionManager transactionManager;
    private final ClientRepository clientRepository;
    private final PhoneRepository phoneRepository;
    private final AddressRepository addressRepository;

    public DbServiceClientImpl(TransactionManager transactionManager, ClientRepository clientRepository, PhoneRepository phoneRepository, AddressRepository addressRepository) {
        this.transactionManager = transactionManager;
        this.clientRepository = clientRepository;
        this.phoneRepository = phoneRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(() -> {
            var savedClient = clientRepository.save(client);
            log.info("saved client: {}", savedClient);
            return savedClient;
        });
    }

    @Override
    public void deleteClient(long clientId) {
        transactionManager.doInTransaction(() -> {
            addressRepository.deleteByClientId(clientId);
            phoneRepository.deleteByClientId(clientId);
            clientRepository.delete(clientId);
            log.info("deleted client by id: {}", clientId);
            return clientId;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        var clientOptional = clientRepository.findById(id);
        log.info("client: {}", clientOptional);
        return clientOptional;
    }

    @Override
    public List<Client> findAll() {
        var clientList = new ArrayList<Client>();
        clientRepository.findAll().forEach(client -> {
            phoneRepository.findByClientId(client.getId()).forEach(client.getPhones()::add);
            clientList.add(client);
        });
        log.info("clientList:{}", clientList);
        return clientList;
    }
}
