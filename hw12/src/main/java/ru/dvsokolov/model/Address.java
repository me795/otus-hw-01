package ru.dvsokolov.model;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class Address implements Cloneable {
    @Id
    @Column(name = "id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street")
    private String street;

    public Address (){}

    public Address (String street){
        this.street = street;
    }

    public Address (Long id, String street){
        this.id = id;
        this.street = street;
    }

    @Override
    public Address clone() { return new Address(this.id, this.street);}

    public Long getId() {
        return id;
    }

    public void setStreet(String street) {
        this.street = street;
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
