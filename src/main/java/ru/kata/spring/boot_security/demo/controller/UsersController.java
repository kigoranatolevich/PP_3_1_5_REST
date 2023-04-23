package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;


@Controller
public class UsersController {

    private final UserService userService;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        return "users/showUsers";
    }

    @GetMapping("/admin/addNewUser")
    public String addNewUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "users/userInfo";
    }

    @PostMapping("/admin/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        Set<Role> roleSet = user.getRole().stream()
                .map(Role::getRole)
                .collect(HashSet::new, (roleHashSet, role) -> roleHashSet.add(roleService.findRoleByName(role)), HashSet::addAll);
        user.setRole(roleSet);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/getExistingUser")
    public String getExistingUser(@RequestParam("userId") int id, Model model) {
        User existingUser = userService.getUser(id);
        model.addAttribute("existingUser", existingUser);
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "users/updateInfo";
    }

    @PatchMapping ("/admin/updateUser")
    public String updateUser(@ModelAttribute("existingUser") User existingUser) {
        Set<Role> roleSet = existingUser.getRole().stream()
                .map(Role::getRole)
                .collect(HashSet::new, (roleHashSet, role) -> roleHashSet.add(roleService.findRoleByName(role)), HashSet::addAll);
        existingUser.setRole(roleSet);
        existingUser.setPassword(passwordEncoder.encode(existingUser.getPassword()));
        userService.updateUser(existingUser);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/deleteUser")
    public String deleteUser(@RequestParam("userId") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String getPofile(Principal principal, Model model) {
        User user = userService.findUserByEmail(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }
}
