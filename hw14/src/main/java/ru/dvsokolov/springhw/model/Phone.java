package ru.dvsokolov.springhw.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;


@Table("phones")
public class Phone {

    @Id
    private final Long id;
    private final String number;

    public Phone(){
        this(null,null);
    }

    public Phone(String number){
        this(null,number);
    }

    @PersistenceConstructor
    public Phone (Long id, String number){
        this.id = id;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }


    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }


}
