package ru.dvsokolov.springhw.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table("addresses")
public class Address {
    @Id
    private final Long id;
    private final String street;

    public Address(){
        this(null,null);
    }

    public Address(String street) {
        this(null, street);
    }

    @PersistenceConstructor
    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
    }


    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                '}';
    }
}
