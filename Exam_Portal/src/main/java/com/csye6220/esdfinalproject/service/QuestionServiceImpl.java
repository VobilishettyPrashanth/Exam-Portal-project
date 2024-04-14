package com.csye6220.esdfinalproject.service;


import com.csye6220.esdfinalproject.dao.QuestionsDAO;
import com.csye6220.esdfinalproject.model.Category;
import com.csye6220.esdfinalproject.model.Exam;
import com.csye6220.esdfinalproject.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionsDAO questionsDAO;

    @Override
    public void addQuestion(Question question) {
         questionsDAO.save(question);
    }

    @Override
    public void updateQuestion(Question question) {
         questionsDAO.update(question);
    }

    @Override
    public Question getQuestionById(Long questionId) {
        return questionsDAO.getQuestionById(questionId) ;
    }

    @Override
    public void deleteQuestion(Long questionId) {

        Question question = new Question();
        question.setQuestionId(questionId);
        this.questionsDAO.delete(question);
    }

    @Override
    public List<Question> getAllQuestionsOfExam(Exam exam) {
        return questionsDAO.getQuestionsByExam(exam);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionsDAO.getAllQuestions();
    }

}
