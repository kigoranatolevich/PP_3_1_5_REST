package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public RoleDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Role> getAllRoles() {
        Query query = entityManager.createQuery("SELECT r FROM Role r");
        return query.getResultList();
    }

    @Override
    public Role findRoleByName(String role) {
        Query query = entityManager.createQuery("SELECT r FROM Role r WHERE r.role = :role");
        query.setParameter("role", role);
        return (Role) query.getSingleResult();
    }
}
