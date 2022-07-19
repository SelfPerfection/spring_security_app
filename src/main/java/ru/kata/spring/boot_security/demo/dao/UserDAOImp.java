package ru.kata.spring.boot_security.demo.dao;

import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.util.List;

@Repository
public class UserDAOImp implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void createUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void editUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(Long id) {
        String jpql = "DELETE FROM User u WHERE u.id=:id";
        int isSuccessful = entityManager.createQuery(jpql)
                .setParameter("id", id)
                .executeUpdate();

        /* Exception example
         *
        if (isSuccessful == 0) {
            throw new SQLException("Can not delete User with id = " + id);
        }
        */
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        String jpql = "SELECT DISTINCT u FROM User u JOIN FETCH u.roles ORDER BY u.id";

        TypedQuery<User> query = entityManager.createQuery(jpql, User.class)
                .setHint( QueryHints.HINT_PASS_DISTINCT_THROUGH, false );
        return query.getResultList();
    }

    @Override
    public User findByUsername(String username) {
        String jpql = "SELECT u FROM User u JOIN FETCH u.roles WHERE u.name=:username";

        return (User) entityManager.createQuery(jpql)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public User findUserByEmail(String email) {
        String jpql = "SELECT u FROM User u JOIN FETCH u.roles WHERE u.email=:email";

        return (User) entityManager.createQuery(jpql)
                .setParameter("email", email)
                .getSingleResult();
    }
}
