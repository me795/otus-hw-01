package ru.dvsokolov.myorm;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        EntityClassMetaData entityClassMetaDataClient = new EntityClassMetaDataImpl(Client.class);
        EntitySQLMetaData entitySQLMetaDataClient = new EntitySQLMetaDataImpl(entityClassMetaDataClient);
        var dataTemplateClient = new DataTemplateJdbc<Client>(dbExecutor, entitySQLMetaDataClient); //реализация DataTemplate, универсальная

// Код дальше должен остаться
        var dbServiceClient = new DbServiceClientImpl(transactionRunner, dataTemplateClient);
        dbServiceClient.saveClient(new Client("dbServiceFirst"));

        var clientSecond = dbServiceClient.saveClient(new Client("dbServiceSecond"));
        var clientSecondSelected = dbServiceClient.getClient(clientSecond.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecond.getId()));
        clientSecondSelected.setName("dbServiceSecondChangeName");
        dbServiceClient.saveClient(clientSecondSelected);
        log.info("clientSecondSelected:{}", clientSecondSelected);

        var clientList = dbServiceClient.findAll();
        log.info("clientList:{}", clientList);

// Сделайте тоже самое с классом Manager (для него надо сделать свою таблицу)

        EntityClassMetaData<Manager> entityClassMetaDataManager = EntityClassMetaDataImpl.of(Manager.class);
        EntitySQLMetaData entitySQLMetaDataManager = new EntitySQLMetaDataImpl(entityClassMetaDataManager);
        var dataTemplateManager = new DataTemplateJdbc<Manager>(dbExecutor, entityClassMetaDataManager, entitySQLMetaDataManager);

        var dbServiceManager = new DbServiceManagerImpl(transactionRunner, dataTemplateManager);
        dbServiceManager.saveManager(new Manager("ManagerFirst"));

        var managerSecond = dbServiceManager.saveManager(new Manager("ManagerSecond"));
        var managerSecondSelected = dbServiceManager.getManager(managerSecond.getNo())
                .orElseThrow(() -> new RuntimeException("Manager not found, id:" + managerSecond.getNo()));
        log.info("managerSecondSelected:{}", managerSecondSelected);
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
