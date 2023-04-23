package ru.kata.spring.boot_security.demo;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class TestClass {

    private final User user;
    private final UserService userService;
    private final RoleService roleService;

    public TestClass(User user, UserService userService, RoleService roleService) {
        this.user = user;
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {
        user.setFirstName("TestUserFirstName");
        user.setLastName("TestUserLastName");
        user.setAge(20);
        user.setEmail("testUserEmail@mail.ru");
        user.setPassword("$2a$12$2VlILHofgAzpmb5XUW6Pcukl0nGKiLMCXCTDojQbILDc2Zjit6aHO");
        Set<Role> roles = Set.of(roleService.findRoleByName("ROLE_ADMIN"));
        user.setRole(roles);
        userService.saveUser(user);
    }
}
