package ru.kata.spring.boot_security.demo;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class TestClass {
    private final User user;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public TestClass(User user, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.user = user;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        user.setFirstName("123");
        user.setLastName("123");
        user.setAge(20);
        user.setEmail("123");
        user.setPassword(passwordEncoder.encode("123"));
        List<Role> roleList = List.of(new Role("ROLE_ADMIN"), new Role("ROLE_USER"));
        roleRepository.saveAll(roleList);
        user.setRole(roleList);
        userRepository.save(user);
    }
}
