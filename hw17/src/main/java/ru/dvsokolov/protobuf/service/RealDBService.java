package ru.dvsokolov.protobuf.service;

import ru.dvsokolov.protobuf.model.User;

import java.util.List;

public interface RealDBService {
    User saveUser(String firstName, String lastName);
    List<User> findAllUsers();
}
