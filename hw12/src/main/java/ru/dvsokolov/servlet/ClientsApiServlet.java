package ru.dvsokolov.servlet;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dvsokolov.dao.ClientDao;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.dvsokolov.model.Client;
import ru.dvsokolov.server.RequestBodyReader;

import java.io.IOException;



public class ClientsApiServlet extends HttpServlet {

    private static final int ID_PATH_PARAM_POSITION = 1;

    private final ClientDao clientDao;
    private final Gson gson;
    private static final Logger log = LoggerFactory.getLogger(RequestBodyReader.class);

    public ClientsApiServlet(ClientDao clientDao, Gson gson) {
        this.clientDao = clientDao;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var client = clientDao.getClient(extractIdFromRequest(request)).orElse(null);

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(gson.toJson(client));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        RequestBodyReader<Client> requestBodyReader = new RequestBodyReader<>(request, Client.class);
        var clientFromRequest = requestBodyReader.getObject();

        if (clientFromRequest != null) {

            var client = clientDao.saveClient(clientFromRequest);

            response.setContentType("application/json;charset=UTF-8");
//            var gsonTest = new Gson();
            ServletOutputStream out = response.getOutputStream();
//            String output = gsonTest.toJson(clientFromRequest);
//            log.info(output);
//            out.print("{}");
            out.print(gson.toJson(client));
        }else{
            response.sendError(400,"Bad Client object");
        }
    }

    private long extractIdFromRequest(HttpServletRequest request) {
        String[] path = request.getPathInfo().split("/");
        String id = (path.length > 1)? path[ID_PATH_PARAM_POSITION]: String.valueOf(- 1);
        return Long.parseLong(id);
    }

}
