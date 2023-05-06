package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

    User findUserByEmail(String email);

    void save(User user);

    void saveAndFlush(User user);

    List<User> findAll();

    Optional<User> findById(int id);

    void deleteById(int id);
}
