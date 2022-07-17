package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    User getUserById(Long id);

    void createUser(User user);

    void deleteUser(Long id);

    List<User> getAllUsers();

    User findUserByEmail(String email);

    void editUser(User user, Long id);
}
