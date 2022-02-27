package ru.dvsokolov.springhw.controllers;

import org.springframework.web.bind.annotation.*;
import ru.dvsokolov.springhw.model.Client;
import ru.dvsokolov.springhw.service.DBServiceClient;

@RestController
public class ClientRestController {

    private final DBServiceClient dbServiceClient;

    public ClientRestController(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @PutMapping("/api/client")
    public Client saveClient(@RequestBody Client client) {
        return dbServiceClient.saveClient(client);
    }

    @DeleteMapping("/api/client/{id}")
    public void removeClientById(@PathVariable(name = "id") long id) {
        dbServiceClient.deleteClient(id);
    }


}
