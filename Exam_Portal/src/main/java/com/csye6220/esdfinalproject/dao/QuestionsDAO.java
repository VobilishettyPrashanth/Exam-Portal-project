package com.csye6220.esdfinalproject.dao;

import com.csye6220.esdfinalproject.model.Exam;
import com.csye6220.esdfinalproject.model.Question;

import javax.swing.plaf.PanelUI;
import java.util.List;
import java.util.Set;

public interface QuestionsDAO {

   public void save(Question question);

    public void update(Question question);

    public void delete(Question question);

    public Question getQuestionById(Long questionId);

    public List<Question> getQuestionsByExam(Exam exam);

   public List<Question> getAllQuestions();

   public List<Question> findQuestionsByExam(Exam exam);


}
