package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleDAO {
    Role getRoleById(int id);

    Role getRoleByRoleName(String name);

    void createRole(Role role);

    void editRole(Role updatedRole);

    void deleteRole(int id);

    List<Role> getRoles();
}
