package com.csye6220.esdfinalproject.service;

import com.csye6220.esdfinalproject.model.Exam;
import com.csye6220.esdfinalproject.model.Question;

import java.util.List;
import java.util.Set;


public interface ExamService {

    public void addExam(Exam exam);

    public void updateExam(Exam exam);

    public Exam getExamById(Long examId);

    public void deleteExam(Long examId);

    public List<Exam> getAllExams();

    public List<Question> getQuestionsOfExam(Long examId);

    public int calculateScore(Long examId, String[] answers);
}
