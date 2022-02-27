package ru.dvsokolov.springhw.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.dvsokolov.springhw.model.Address;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, Long> {

    @Modifying
    @Query("update addresses set deleted_at = now() where client_id = :client_id")
    void deleteByClientId(@Param("client_id") long id);
}
