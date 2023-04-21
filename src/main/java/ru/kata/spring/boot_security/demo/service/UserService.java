package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    void saveUser(User user);

    void updateUser(User user);

    User getUser(int id);

    void deleteUser(int id);

    User findUserByEmail(String email);

    @Override
    UserDetails loadUserByUsername(String username);
}
