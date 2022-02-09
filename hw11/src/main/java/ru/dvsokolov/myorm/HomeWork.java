package ru.dvsokolov.myorm;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dvsokolov.cachehw.DecoratorServiceClient;
import ru.dvsokolov.cachehw.HwCache;
import ru.dvsokolov.cachehw.HwListener;
import ru.dvsokolov.cachehw.MyCache;
import ru.dvsokolov.core.repository.executor.DbExecutorImpl;
import ru.dvsokolov.core.sessionmanager.TransactionRunnerJdbc;
import ru.dvsokolov.crm.datasource.DriverManagerDataSource;
import ru.dvsokolov.crm.model.Client;
import ru.dvsokolov.crm.model.Manager;
import ru.dvsokolov.crm.service.DbServiceClientImpl;
import ru.dvsokolov.crm.service.DbServiceManagerImpl;
import ru.dvsokolov.myorm.jdbc.mapper.*;

import javax.sql.DataSource;

public class HomeWork {
    private static final String URL = "jdbc:postgresql://localhost:5430/demoDB";
    private static final String USER = "usr";
    private static final String PASSWORD = "pwd";

    private static final Logger log = LoggerFactory.getLogger(HomeWork.class);

    public static void main(String[] args) {
// Общая часть
        var dataSource = new DriverManagerDataSource(URL, USER, PASSWORD);
        flywayMigrations(dataSource);
        var transactionRunner = new TransactionRunnerJdbc(dataSource);
        var dbExecutor = new DbExecutorImpl();

// Работа с клиентом
        EntityClassMetaData<Client> entityClassMetaDataClient = EntityClassMetaDataImpl.of(Client.class);
        EntitySQLMetaData entitySQLMetaDataClient = new EntitySQLMetaDataImpl(entityClassMetaDataClient);
        var dataTemplateClient = new DataTemplateJdbc<>(dbExecutor, entityClassMetaDataClient, entitySQLMetaDataClient); //реализация DataTemplate, универсальная

        var dbServiceClient = new DbServiceClientImpl(transactionRunner, dataTemplateClient);

        HwListener<String, Client> listener = new HwListener<>() {
            @Override
            public void notify(String key, Client value, String action) {
                log.info("cache! key:{}, value:{}, action: {}", key, value, action);
            }
        };
        HwCache<String, Client> cache = new MyCache<>();
        cache.addListener(listener);

        var decoratorServiceClient = new DecoratorServiceClient(dbServiceClient, cache);

        for (long i = 1L; i < 1000L; i++){
            String name = "name" + i;
            decoratorServiceClient.saveClient(new Client(name));
        }
        for (long j = 999L; j > 0; j--){
            long finalJ = j;
            var selectedClient = decoratorServiceClient.getClient(j)
                    .orElseThrow(() -> new RuntimeException("Client not found, id:" + finalJ));
            log.info("selectedClient:{}", selectedClient);
        }
    }

    private static void flywayMigrations(DataSource dataSource) {
        log.info("db migration started...");
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db/migration")
                .load();
        flyway.migrate();
        log.info("db migration finished.");
        log.info("***");
    }
}
