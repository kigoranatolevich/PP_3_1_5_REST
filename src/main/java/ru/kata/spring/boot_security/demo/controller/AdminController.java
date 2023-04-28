package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
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
import ru.kata.spring.boot_security.demo.repository.UserRepository;

@Controller
public class AdminController {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public AdminController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model) {
        model.addAttribute("allUsers", userRepository.findAll());
        return "users/showUsers";
    }

    @GetMapping("/admin/addNewUser")
    public String addNewUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleRepository.findAll());
        return "users/userInfo";
    }

    @PostMapping("/admin/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/getExistingUser")
    public String getExistingUser(@RequestParam("userId") int id, Model model) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NullPointerException(String.format("User not found by %d", id)));
        model.addAttribute("existingUser", existingUser);
        model.addAttribute("allRoles", roleRepository.findAll());
        return "users/updateInfo";
    }

    @PatchMapping ("/admin/updateUser")
    public String updateUser(@ModelAttribute("existingUser") User existingUser) {
        existingUser.setPassword(passwordEncoder.encode(existingUser.getPassword()));
        userRepository.save(existingUser);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/deleteUser")
    public String deleteUser(@RequestParam("userId") int id) {
        userRepository.deleteById(id);
        return "redirect:/admin";
    }
}
