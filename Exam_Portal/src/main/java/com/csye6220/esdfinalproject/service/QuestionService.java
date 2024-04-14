package com.csye6220.esdfinalproject.service;

import com.csye6220.esdfinalproject.model.Exam;
import com.csye6220.esdfinalproject.model.Question;

import java.util.List;
import java.util.Set;

public interface QuestionService {
    public void addQuestion(Question question);

    public void updateQuestion(Question question);

    public Question getQuestionById(Long questionId);

    public void deleteQuestion(Long quesId);

    public List<Question> getAllQuestionsOfExam(Exam exam);

    public List<Question> getAllQuestions();

}
