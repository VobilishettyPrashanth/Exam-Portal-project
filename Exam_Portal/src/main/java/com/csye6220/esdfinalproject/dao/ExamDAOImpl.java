package com.csye6220.esdfinalproject.dao;

import com.csye6220.esdfinalproject.model.Exam;
import com.csye6220.esdfinalproject.model.Question;
import com.csye6220.esdfinalproject.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import java.util.List;


@Component
public class ExamDAOImpl implements ExamDAO{
    private SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

    @Override
    public void save(Exam exam) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.persist(exam);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error saving exam to the database: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Exam exam) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.merge(exam);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error Updating exam to the database: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Exam exam) {
        if(exam == null)
            return;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.remove(exam);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error deleting exam from database: " + e.getMessage(), e);
        }
    }

    @Override
    public Exam getExamById(Long examId) {
        try(Session session = sessionFactory.openSession()) {
            String queryString = "FROM Exam e where e.examId =" + examId ;
            Query<Exam> query = session.createQuery(queryString, Exam.class);
            List<Exam> exams = query.list();
            return exams.size() == 1 ? exams.get(0) : null;
        }
        catch (Exception e){
            throw new RuntimeException("Exam with this Id Not Found in database: " + e.getMessage(), e);
        }
    }


    @Override
    public List<Exam> findAllExams() {
        try (Session session = sessionFactory.openSession()) {
            String queryString = "SELECT e FROM Exam e";
            Query<Exam> query = session.createQuery(queryString, Exam.class);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("No Exams Found in the database: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Question> getAllQuestion(Long examId) {
        try (Session session = sessionFactory.openSession()) {
            String queryString = "FROM Question q where q.exam.id="+examId;
            Query<Question> query = session.createQuery(queryString, Question.class);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("No Exams Found in the database: " + e.getMessage(), e);
        }
    }
}
