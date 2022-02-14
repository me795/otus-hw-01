package ru.dvsokolov.cachehw;

import ru.dvsokolov.crm.model.Client;
import ru.dvsokolov.crm.service.DBServiceClient;

import java.util.List;
import java.util.Optional;

public class DecoratorServiceClient implements DBServiceClient {

    private DBServiceClient dbServiceClient;
    private HwCache<String, Client> cache;


    public DecoratorServiceClient(DBServiceClient dbServiceClient, HwCache<String, Client> cache){
        this.dbServiceClient = dbServiceClient;
        this.cache = cache;
    }

    @Override
    public Client saveClient(Client client) {
        if (client.getId() != null){
            cache.remove(client.getId().toString());
        }
        var savedClient = dbServiceClient.saveClient(client);
        cache.put(savedClient.getId().toString(),savedClient);

        return savedClient;
    }

    @Override
    public Optional<Client> getClient(long id) {

        var cachedClient = cache.get(String.valueOf(id));
        if (cachedClient == null) {
            var dbClient = dbServiceClient.getClient(id);
            dbClient.ifPresent(client -> cache.put(client.getId().toString(), client));
            return dbClient;
        }else{
            return Optional.of(cachedClient);
        }
    }

    @Override
    public List<Client> findAll() {
            return dbServiceClient.findAll();
    }
}
