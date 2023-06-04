package ru.kata.spring.boot_security.demo.util;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entity.Authority;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;
import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class DataLoader {
    private final User user;
    private final UserService userService;
    private final RoleRepository roleRepository;

    public DataLoader(User user, UserService userService, RoleRepository roleRepository) {
        this.user = user;
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void loadData() {
        user.setFirstName("123");
        user.setLastName("123");
        user.setAge(20);
        user.setEmail("123@gmail.com");
        user.setPassword("123");
        Set<Authority> authorityList = Set.of(new Authority("ADMIN"), new Authority("USER"));
        roleRepository.saveAll(authorityList);
        user.setAuthorities(authorityList);
        userService.save(user);
    }
}
