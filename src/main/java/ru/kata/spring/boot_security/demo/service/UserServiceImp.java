package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;


import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private final UserDAO userDao;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserServiceImp(UserDAO userDao, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    @Transactional
    public void createUser(User user) {
        List<Role> roles = new ArrayList<>();
        List<Role> rolesForm = user.getRoles();

        // Check roles, if roles are empty - set default ROLE_USER
        if (!rolesForm.isEmpty()) {
            for (Role role : rolesForm) {
                roles.add(roleService.getRoleByRoleName(role.getRoleName()));
            }
        } else {
            roles.add(roleService.getRoleByRoleName("ROLE_USER"));
        }

        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.createUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    @Transactional
    public void editUser(User user, Long id) {
        List<Role> roles = new ArrayList<>();
        List<Role> rolesForm = user.getRoles();

        // Check roles, if roles are empty - set default ROLE_USER
        if (!rolesForm.isEmpty()) {
            for (Role role : rolesForm) {
                roles.add(roleService.getRoleByRoleName(role.getRoleName()));
            }
        } else {
            roles.add(roleService.getRoleByRoleName("ROLE_USER"));
        }

        // Check password, if password is empty or null - get password from db by user id, else set password from form
        if (user.getPassword().equals("") || user.getPassword() == null) {
            user.setPassword(getUserById(id).getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        user.setRoles(roles);
        userDao.editUser(user);
    }
}
