package ru.dvsokolov.springhw.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.dvsokolov.springhw.model.Client;
import ru.dvsokolov.springhw.model.Phone;

import java.util.List;


public interface ClientRepository extends CrudRepository<Client, Long> {

    @Query(value = """
            SELECT
                    clients.id AS id,
                    clients.name AS name,
                    address.id AS address_id,
                    address.street AS address_street
            FROM clients
                    LEFT OUTER JOIN addresses address
                    ON address.client_id = clients.id
            WHERE
                    clients.deleted_at IS NULL
                    AND
                    address.deleted_at IS NULL
                    """,
            rowMapperClass = ClientMapper.class)
    List<Client> findAll();

    @Query("select * from phones where client_id = :clientId and deleted_at is null")
    List<Phone> findByClient(@Param("clientId") long clientId);

    @Modifying
    @Query("update clients set deleted_at = now() where id = :id")
    void delete(@Param("id") long id);
}
