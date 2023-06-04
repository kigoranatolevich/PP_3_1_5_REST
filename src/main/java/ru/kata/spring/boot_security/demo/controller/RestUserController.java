package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.model.CustomUserDetails;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.util.Collections;
import java.util.List;

@RestController
public class RestUserController {

    private final UserService userService;

    public RestUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/showUser")
    public ResponseEntity<List<UserDTO>> getProfile(Authentication authentication) {
        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        StringBuilder stringBuilder = new StringBuilder();
        user.getAuthorities().forEach(authorities -> stringBuilder.append(authorities.getAuthority()).append(" "));
        return ResponseEntity.status(HttpStatus.OK)
                .header("navbar", String.format("%s with roles: %s", user.getEmail(), stringBuilder))
                .body(Collections.singletonList(userService.convertToUserDTO(user)));
    }
}