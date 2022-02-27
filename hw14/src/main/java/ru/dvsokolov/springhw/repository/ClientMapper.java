package ru.dvsokolov.springhw.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import ru.dvsokolov.springhw.model.Address;
import ru.dvsokolov.springhw.model.Client;
import ru.dvsokolov.springhw.model.Phone;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientMapper implements RowMapper<Client> {

    @Override
    public Client mapRow(ResultSet rs, int i) throws SQLException {

        var clientId = rs.getLong("id");
        var clientName = rs.getString("name");
        var addressId = rs.getLong("address_id");
        var addressStreet = rs.getString("address_street");

        return(addressId == 0) ?
                    new Client(clientId, clientName, null, new ArrayList<>()) :
                    new Client(clientId, clientName, new Address(addressId, addressStreet), new ArrayList<>());
    }
}
