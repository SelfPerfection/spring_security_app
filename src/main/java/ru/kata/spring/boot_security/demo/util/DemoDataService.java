package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
public class DemoDataService implements CommandLineRunner {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public DemoDataService(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @Override
    public void run(String... args) throws Exception {
        try {
            Role role1 = new Role("ROLE_ADMIN");
            Role role2 = new Role("ROLE_USER");
            roleService.createRole(role1);
            roleService.createRole(role2);

            List<Role> roleAdmin = new ArrayList<>();
            List<Role> roleUser = new ArrayList<>();
            roleAdmin.add(role1);
            roleUser.add(role2);

            List<Role> superUser = new ArrayList<>();
            superUser.add(role1);
            superUser.add(role2);

            User user1 = new User("Jarvis", "AI", "jarvis@stark.ind", 28,
                    "jarvis1234", roleAdmin);
            User user2 = new User("Bruce", "Banner", "brucebanner@gmail.com", 37,
                    "useruser", roleUser);
            User user3 = new User("Admin", "Nimda", "admin@localhost", 77,
                    "adminadmin", superUser);

            userService.createUser(user1);
            userService.createUser(user2);
            userService.createUser(user3);
        } catch (Exception e) {
            System.out.println("DB is NOT empty. Please check. " + e.getMessage());
            throw e;
        }
    }
}
