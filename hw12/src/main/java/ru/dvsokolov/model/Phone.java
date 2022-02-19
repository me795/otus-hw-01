package ru.dvsokolov.model;

import javax.persistence.*;

@Entity
@Table(name = "phones")
public class Phone implements Cloneable {

    @Id
    @Column(name = "id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    private String number;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "client_id",nullable = false)
//    private Client client;

    public Phone () {}

    public Phone (String number){
        this.number = number;
    }

    public Phone (Long id, String number){
        this.id = id;
        this.number = number;
    }

    @Override
    public Phone clone() { return new Phone(this.id, this.number);}

    public Long getId() {
        return id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

//    public void setClient(Client client) {
//        this.client = client;
//    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }


}
