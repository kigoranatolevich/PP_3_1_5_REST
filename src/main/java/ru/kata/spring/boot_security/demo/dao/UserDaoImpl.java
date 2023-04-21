package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        Query query = entityManager.createQuery("SELECT u FROM User u");
        return query.getResultList();
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public User getUser(int id) {
        Query query = entityManager.createQuery("Select u from User u where u.id = :id");
        query.setParameter("id", id);
        return (User) query.getSingleResult();
    }

    @Override
    public User findUserByEmail(String email) {
        Query query = entityManager.createQuery("Select u from User u where u.email = :email");
        query.setParameter("email", email);
        return (User) query.getSingleResult();
    }

    @Override
    public void deleteUser(int id) {
        Query query = entityManager.createQuery("DELETE FROM User WHERE id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
