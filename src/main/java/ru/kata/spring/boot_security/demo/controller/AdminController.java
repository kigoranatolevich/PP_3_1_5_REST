package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.model.CustomUserDetails;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AdminController {

    private final UserService userService;

    private final RoleRepository roleRepository;

    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/showUsers")
    public ResponseEntity<List<UserDTO>> showAllUsers(Authentication authentication) {
        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        StringBuilder stringBuilder = new StringBuilder();
        user.getRoles().forEach(roles -> stringBuilder.append(roles.getAuthority()).append(" "));
        return ResponseEntity.status(HttpStatus.OK)
                .header("navbar", String.format("%s with roles: %s", user.getEmail(), stringBuilder))
                .body(userService.findAll().stream().map(userService::convertToUserDTO).collect(Collectors.toList()));
    }

    @GetMapping("/showRoles")
    public List<Role> showAllRoles() {
        return roleRepository.findAll();
    }

    @PostMapping("/saveUser")
    public ResponseEntity<User> saveUser(@RequestBody UserDTO userDTO) {
        User user = userService.convertToUser(userDTO);
        userService.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PatchMapping ("/updateUser")
    public ResponseEntity<User> updateUser(@RequestBody UserDTO userDTO) {
        User user = userService.convertToUser(userDTO);
        userService.update(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<HttpStatus> deleteUser(@RequestBody UserDTO userDTO) {
        userService.deleteById(userService.convertToUser(userDTO).getId());
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
