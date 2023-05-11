package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class AdminController {

    private final UserService userService;

    private final RoleRepository roleRepository;

    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model, Authentication authentication) {
        User user = new User();
        StringBuilder stringBuilder = new StringBuilder();
        authentication.getAuthorities().forEach(roles -> stringBuilder.append(roles.getAuthority()).append(" "));
        model.addAttribute("newUser", user);
        model.addAttribute("allUsers", userService.findAll());
        model.addAttribute("allRoles", roleRepository.findAll());
        model.addAttribute("currentUser", authentication.getPrincipal());
        model.addAttribute("authorizedUser", String.format("%s with roles: %s", authentication.getName(), stringBuilder));
        return "users/showUsers";
    }

    @PostMapping("/admin/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @PatchMapping ("/admin/updateUser")
    public String updateUser(@ModelAttribute("editUser") User existingUser) {
        userService.update(existingUser);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/deleteUser")
    public String deleteUser(@RequestParam("userId") int id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}
