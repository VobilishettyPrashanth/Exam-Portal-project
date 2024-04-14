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
public class QuestionDAOImpl implements QuestionsDAO{

    private SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

    @Override
    public void save(Question question) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.persist(question);
            transaction.commit();

        } catch (Exception e) {
            throw new RuntimeException("Error saving the question to the database: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Question question) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.merge(question);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error Updating question in the database: " + e.getMessage(), e);
        }
    }

    @Override
    public Question getQuestionById(Long questionId) {
        try(Session session = sessionFactory.openSession()) {
            String queryString = "FROM Question q where q.questionId =" + questionId;
            Query<Question> query = session.createQuery(queryString, Question.class);
            List<Question> questions = query.list();
            return questions.size() == 1 ? questions.get(0) : null;
        }
        catch (Exception e){
            throw new RuntimeException("Category with this Question Id is Not Found in database: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Question> getQuestionsByExam(Exam exam) {
        try (Session session = sessionFactory.openSession()) {
            String queryString = "SELECT q FROM Question q WHERE q.exam = :exam";
            Query<Question> query = session.createQuery(queryString, Question.class);
            query.setParameter("exam", exam);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching questions by exam: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Question> getAllQuestions() {
        try (Session session = sessionFactory.openSession()) {
            String queryString = "FROM Question ";
            Query<Question> query = session.createQuery(queryString, Question.class);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("No Questions Found in the database: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Question> findQuestionsByExam(Exam exam) {
        try (Session session = sessionFactory.openSession()) {
            String queryString = "SELECT COUNT(*) FROM Question q WHERE q.exam = :exam";
            Query<Question> query = session.createQuery(queryString, Question.class);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("No Questions Found in the database: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Question question) {

        if(question == null)
            return;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.remove(question);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error deleting the question from database: " + e.getMessage(), e);
        }
    }
}
