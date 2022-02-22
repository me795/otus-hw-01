package ru.dvsokolov.servlet;

import ru.dvsokolov.dao.ClientDao;
import ru.dvsokolov.services.TemplateProcessor;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ClientsServlet extends HttpServlet {

    private static final String HEADER_PAGE_TEMPLATE = "header.html";
    private static final String BODY_PAGE_TEMPLATE = "clients.html";
    private static final String FOOTER_PAGE_TEMPLATE = "footer.html";
    private static final String TEMPLATE_ATTR_ALL_CLIENTS = "clientList";
    private static final String TEMPLATE_ATTR_VERSION_SCRIPTS = "version";
    private static final String VERSION_SCRIPTS = "14";

    private final ClientDao clientDao;
    private final TemplateProcessor templateProcessor;

    public ClientsServlet(TemplateProcessor templateProcessor, ClientDao clientDao) {
        this.templateProcessor = templateProcessor;
        this.clientDao = clientDao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        var clients = clientDao.findAll();
        paramsMap.put(TEMPLATE_ATTR_ALL_CLIENTS, clients);
        paramsMap.put(TEMPLATE_ATTR_VERSION_SCRIPTS, VERSION_SCRIPTS);


        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(HEADER_PAGE_TEMPLATE, paramsMap));
        response.getWriter().println(templateProcessor.getPage(BODY_PAGE_TEMPLATE, paramsMap));
        response.getWriter().println(templateProcessor.getPage(FOOTER_PAGE_TEMPLATE, paramsMap));
    }

}
