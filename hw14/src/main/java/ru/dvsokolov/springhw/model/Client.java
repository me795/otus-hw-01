package ru.dvsokolov.springhw.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;

@Table("clients")
public class Client {

    @Id
    private final Long id;
    private final String name;
    @MappedCollection(idColumn = "client_id")
    private final Address address;
    @MappedCollection(keyColumn = "client_id", idColumn = "client_id")
    private final List<Phone> phones;

    public Client(){
        this(null, null, null, new ArrayList<>());
    }

    public Client(String name, Address address, List<Phone> phones){
        this(null, name, address, phones);
    }

    @PersistenceConstructor
    public Client(Long id, String name, Address address, List<Phone> phones){
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phones='" + phones + '\'' +
                '}';
    }
}