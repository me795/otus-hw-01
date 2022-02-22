package ru.dvsokolov;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.hibernate.cfg.Configuration;
import ru.dvsokolov.dao.DbClientDao;
import ru.dvsokolov.helpers.FileSystemHelper;
import ru.dvsokolov.jpql.core.repository.DataTemplateHibernate;
import ru.dvsokolov.jpql.core.repository.HibernateUtils;
import ru.dvsokolov.jpql.core.sessionmanager.TransactionManagerHibernate;
import ru.dvsokolov.jpql.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.dvsokolov.model.Address;
import ru.dvsokolov.model.Client;
import ru.dvsokolov.model.Phone;
import ru.dvsokolov.server.ClientsWebServer;
import ru.dvsokolov.server.ClientsWebServerWithBasicSecurity;
import ru.dvsokolov.services.TemplateProcessor;
import ru.dvsokolov.services.TemplateProcessorImpl;

public class MyWebServer {
    private static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HASH_LOGIN_SERVICE_CONFIG_NAME = "realm.properties";
    private static final String REALM_NAME = "AnyRealm";


    public static void main(String[] args) throws Exception {

        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        var dbClientDao = new DbClientDao(transactionManager, clientTemplate);

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        String hashLoginServiceConfigPath = FileSystemHelper.localFileNameOrResourceNameToFullPath(HASH_LOGIN_SERVICE_CONFIG_NAME);
        LoginService loginService = new HashLoginService(REALM_NAME, hashLoginServiceConfigPath);

        ClientsWebServer clientsWebServer = new ClientsWebServerWithBasicSecurity(WEB_SERVER_PORT,
                loginService, dbClientDao, gson, templateProcessor);

        clientsWebServer.start();
        clientsWebServer.join();
    }

}
