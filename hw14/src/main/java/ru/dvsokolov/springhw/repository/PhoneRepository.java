package ru.dvsokolov.springhw.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.dvsokolov.springhw.model.Phone;

import java.util.List;

public interface PhoneRepository extends CrudRepository<Phone, Long> {

    @Query("select * from phones where client_id = :clientId and deleted_at is null")
    List<Phone> findByClientId(@Param("clientId") long clientId);

    @Modifying
    @Query("update phones set deleted_at = now() where client_id = :client_id")
    void deleteByClientId(@Param("client_id") long id);
}
