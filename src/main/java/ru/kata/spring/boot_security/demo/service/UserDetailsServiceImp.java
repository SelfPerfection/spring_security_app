package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.details.AppUserDetails;
import ru.kata.spring.boot_security.demo.model.User;


@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserDAO userDAO;

    @Autowired
    public UserDetailsServiceImp(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDAO.findUserByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User not found!", email));
        }
        return new AppUserDetails(user);
    }
}