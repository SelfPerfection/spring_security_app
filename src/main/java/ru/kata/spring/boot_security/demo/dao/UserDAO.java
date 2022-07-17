package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDAO {

    User getUserById(Long id);

    void createUser(User user);

    void editUser(User user);

    void deleteUser(Long id);

    List<User> getAllUsers();

    User findByUsername(String username);

    User findUserByEmail(String email);
}

