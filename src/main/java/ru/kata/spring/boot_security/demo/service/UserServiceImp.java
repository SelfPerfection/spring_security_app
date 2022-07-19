package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;


import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private final RoleDAO roleDAO;

    @Autowired
    public UserServiceImp(UserDAO userDAO, PasswordEncoder passwordEncoder, RoleDAO roleDAO) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
        this.roleDAO = roleDAO;
    }

    @Override
    public User getUserById(Long id) {
        return userDAO.getUserById(id);
    }

    @Override
    @Transactional
    public void createUser(User user) {
        user.setRoles(checkRoles(user.getRoles()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.createUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userDAO.deleteUser(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public User findUserByEmail(String email) {
        return userDAO.findUserByEmail(email);
    }

    @Override
    @Transactional
    public void editUser(User user, Long id) {

        // Check password, if password is empty or null - get password from db by user id, else set password from form
        if (user.getPassword().equals("") || user.getPassword() == null) {
            user.setPassword(getUserById(id).getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        user.setRoles(checkRoles(user.getRoles()));
        userDAO.editUser(user);
    }

    /*
     * Method check roles, if roles are empty - set default ROLE_USER
     * Return List<Role>
     * @param List<Role> rolesForm
     * @return List<Role>
     */
    private List<Role> checkRoles(List<Role> rolesForm) {
        List<Role> roles = new ArrayList<>();

        if (!rolesForm.isEmpty()) {
            for (Role role : rolesForm) {
                roles.add(roleDAO.getRoleByRoleName(role.getRoleName()));
            }
        } else {
            roles.add(roleDAO.getRoleByRoleName("ROLE_USER"));
        }
        return roles;
    }
}
