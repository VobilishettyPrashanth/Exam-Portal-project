package com.csye6220.esdfinalproject.dao;

import com.csye6220.esdfinalproject.model.Exam;
import com.csye6220.esdfinalproject.model.Question;

import java.util.List;
public interface ExamDAO {

   public void save(Exam exam);

    public void update(Exam exam);

    public void delete(Exam exam);

   public Exam getExamById(Long examId);

//   public void getAllExams(Exam exam);

    public List<Exam> findAllExams();
    public List<Question> getAllQuestion(Long examId);

}
