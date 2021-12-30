package com.voitenkov.sergei.services;

import com.voitenkov.sergei.entities.User;

import java.util.List;

public interface UserService {
    User createUser(User user) throws EntityValidationException;

    User editUser(User user) throws EntityValidationException;

    User getUser(long id);

    void deleteUser(long id);

    List<User> getUsers();
}