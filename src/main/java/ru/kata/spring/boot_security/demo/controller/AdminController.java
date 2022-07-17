package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.details.AppUserDetails;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;


    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String userList(@AuthenticationPrincipal AppUserDetails userDetails, Model model) {
        model.addAttribute("title", "User Dashboard");
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", userService.findUserByEmail(userDetails.getUserEmail()));
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.getRoles());
        return "admin";
    }

    @PostMapping("/new_user")
    public String createUser(@ModelAttribute("user") User user) {
        userService.createUser(user);
        return "redirect:/admin";
    }


    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PatchMapping("/edit/{id}")
    public String editUser(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userService.editUser(user, id);
        return "redirect:/admin";
    }

    /* Check post data (for debug)
    @PatchMapping("/edit/{id}")
    @ResponseBody
    public String editUser(@ModelAttribute("user") User user,
                           @RequestParam(name="roles") String[] roles,
                           @PathVariable("id") Long id,
                           @RequestParam Map<String, String> allParams) {
        return "Parameters are " + allParams.entrySet() + roles.toString() + "<p>" + user.toString() + "</p>";
    }
    */

}
