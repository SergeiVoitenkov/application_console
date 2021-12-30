package com.voitenkov.sergei.repositories;

import com.voitenkov.sergei.entities.User;

import java.util.List;

public interface UserRepository {
    User createUser(User user);

    User editUser(User user);

    User getUser(long id);

    void deleteUser(long id);

    List<User> getUsers();
}