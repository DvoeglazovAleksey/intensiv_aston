package org.example.repository;

import org.example.config.SessionFactoryConfig;
import org.example.entity.User;
import org.example.exception.SqlException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final SessionFactoryConfig sessionFactoryConfig;

    public UserRepositoryImpl(SessionFactoryConfig sessionFactoryConfig) {
        this.sessionFactoryConfig = sessionFactoryConfig;
    }

    public User findById(long id) {
        try (Session session = sessionFactoryConfig.getSession()) {
            return session.get(User.class, id);
        } catch (HibernateException e) {
            throw new SqlException("There was a problem with the database query = findById");
        }
    }

    public User findByLogin(String login) {
        try (Session session = sessionFactoryConfig.getSession()) {
            Query query = session.createQuery("from User where login = :paramName");
            query.setParameter("paramName", login);
            List<User> users = query.getResultList();
            if (users.isEmpty()) {
                return null;
            }
            return users.get(0);
        } catch (HibernateException e) {
            throw new SqlException("There was a problem with the database query = findByLogin");
        }
    }

    public List<User> getAll() {
        try (Session session = sessionFactoryConfig.getSession()) {
            return (List<User>) session.createCriteria(User.class).list();
        } catch (HibernateException e) {
            throw new SqlException("There was a problem with the database query = getAll");
        }
    }

    public User addAndUpdate(User user) {
        try (Session session = sessionFactoryConfig.getSession()) {
            session.beginTransaction();
            session.saveOrUpdate(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            throw new SqlException("There was a problem with the database query = addUser");
        }
        return user;
    }
}
