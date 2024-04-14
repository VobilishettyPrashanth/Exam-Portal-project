package com.csye6220.esdfinalproject.dao;

import com.csye6220.esdfinalproject.model.Category;
import com.csye6220.esdfinalproject.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import java.util.List;


@Component
public class CategoryDAOImpl implements CategoryDAO {

    private SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
    @Override
    public void save(Category category) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.persist(category);
            transaction.commit();

        } catch (Exception e) {
            throw new RuntimeException("Error saving the category to the database: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Category category) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.merge(category);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error Updating category in the database: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Category category) {
        if(category == null)
            return;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.remove(category);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error deleting category in the database: " + e.getMessage(), e);
        }

    }

    @Override
    public List<Category> getAllCategories() {
        try (Session session = sessionFactory.openSession()) {
            String queryString = "FROM Category ";
            Query<Category> query = session.createQuery(queryString, Category.class);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("No Categories Found in the database: " + e.getMessage(), e);
        }
    }


    @Override
    public Category getCategoryById(Long categoryId) {
        try(Session session = sessionFactory.openSession()) {
            String queryString = "FROM Category c where c.categoryId  =" + categoryId;
            Query<Category> query = session.createQuery(queryString, Category.class);
            List<Category> categories = query.list();
            return categories.size() == 1 ? categories.get(0) : null;
        }
        catch (Exception e){
            throw new RuntimeException("Category with this Id is Not Found in database: " + e.getMessage(), e);
        }
    }
}
