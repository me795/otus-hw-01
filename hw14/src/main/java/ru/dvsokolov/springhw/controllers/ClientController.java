package ru.dvsokolov.springhw.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.dvsokolov.springhw.model.Client;
import ru.dvsokolov.springhw.service.DBServiceClient;


import java.util.List;

@Controller
public class ClientController {

    private final String scriptsVersion;
    private final DBServiceClient dbServiceClient;

    public ClientController(@Value("${app.scripts.version}") String scriptsVersion,
                            DBServiceClient dbServiceClient) {
        this.scriptsVersion = scriptsVersion;
        this.dbServiceClient = dbServiceClient;
    }

    @GetMapping({"/", "/client/list"})
    public String clientsListView(Model model) {
        List<Client> clients = dbServiceClient.findAll();
        model.addAttribute("scriptsVersion", scriptsVersion);
        model.addAttribute("clients", clients);
        return "clients";
    }

}
