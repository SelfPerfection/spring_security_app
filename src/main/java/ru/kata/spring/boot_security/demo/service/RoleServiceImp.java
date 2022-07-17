package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

@Service
public class RoleServiceImp implements RoleService {

    private final RoleDAO roleDAO;

    @Autowired
    public RoleServiceImp(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }


    @Override
    public Role getRoleById(int id) {
        return roleDAO.getRoleById(id);
    }

    @Override
    public Role getRoleByRoleName(String name) {
        return roleDAO.getRoleByRoleName(name);
    }

    @Override
    @Transactional
    public void createRole(Role role) {
        roleDAO.createRole(role);
    }


    @Override
    @Transactional
    public void editRole(Role updatedRole) {
        roleDAO.editRole(updatedRole);
    }


    @Override
    @Transactional
    public void deleteRole(int id) {
        roleDAO.deleteRole(id);
    }

    @Override
    public List<Role> getRoles() {
        return roleDAO.getRoles();
    }

}
