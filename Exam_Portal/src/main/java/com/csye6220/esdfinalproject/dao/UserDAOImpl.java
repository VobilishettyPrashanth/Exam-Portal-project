package com.csye6220.esdfinalproject.dao;

import com.csye6220.esdfinalproject.model.User;
import com.csye6220.esdfinalproject.model.UserRole;
import com.csye6220.esdfinalproject.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import java.util.List;


@Component
public class UserDAOImpl implements UserDAO{

    private SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

    @Override
    public User findByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            String string = "FROM User u where u.username=\"" + username + "\"";
            Query query = session.createQuery(string, User.class);
            List<User> users = query.list();
            return users.size() == 1 ? users.get(0) : null;
        } catch (Exception e) {
            throw new RuntimeException("Error finding user with this username: " + e.getMessage(), e);
        }
    }

    @Override
    public Long getUserCountByRole(UserRole role) {

        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT COUNT(*) FROM User u WHERE u.role = :role";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("role", role);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Error getting user count by role: " + e.getMessage(), e);
        }
    }

    @Override
    public void save(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error saving the user to the database: " + e.getMessage(), e);
        }
    }
    public void update(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.merge(user);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error Updating the user in the database: " + e.getMessage(), e);
        }

    }

    @Override
    public void delete(User user) {
        if(user == null)
            return;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.remove(user);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error deleting this user in database: " + e.getMessage(), e);
        }


    }

    @Override
    public void deleteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            if (user != null) {
                session.beginTransaction();
                session.remove(user);
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting user with this id: " + e.getMessage(), e);
        }

    }

    @Override
    public void deleteByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, email);
            if (user != null) {
                session.beginTransaction();
                session.remove(user);
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting user with this Email: " + e.getMessage(), e);
        }
    }


    @Override
    public User getById(Long id) {
        try(Session session = sessionFactory.openSession()) {
            String queryString = "FROM User u where u.id="+id  ;
            Query<User> query = session.createQuery(queryString, User.class);
            List<User> users = query.list();
            return users.size() == 1 ? users.get(0) : null;
        }
        catch (Exception e){
            throw new RuntimeException("User with this is Not Found in database: " + e.getMessage(), e);
        }
    }

    @Override
    public User getByEmail(String email) {
        try(Session session = sessionFactory.openSession()) {
            String queryString = "FROM User u where u.email=\"" + email + "\"";
            Query<User> query = session.createQuery(queryString, User.class);
            List<User> users = query.list();
            return users.size() == 1 ? users.get(0) : null;
        }
        catch (Exception e){
            throw new RuntimeException("User with email id Not Found in database: " + e.getMessage(), e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            String queryString = "FROM User ";
            Query<User> query = session.createQuery(queryString, User.class);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("No User Found in the database: " + e.getMessage(), e);
        }
    }
}
