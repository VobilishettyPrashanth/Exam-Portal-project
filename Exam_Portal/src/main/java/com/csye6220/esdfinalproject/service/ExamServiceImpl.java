package com.csye6220.esdfinalproject.service;

import com.csye6220.esdfinalproject.dao.ExamDAO;
import com.csye6220.esdfinalproject.model.Exam;
import com.csye6220.esdfinalproject.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;


@Service
public class ExamServiceImpl implements ExamService{

    @Autowired
    private ExamDAO examDAO;

    @Override
    public void addExam(Exam exam) {
        examDAO.save(exam);
    }

    @Override
    public void updateExam(Exam exam) {
        examDAO.update(exam);
    }

    @Override
    public Exam getExamById(Long examId) {
        return  examDAO.getExamById(examId);
    }

    @Override
    public void deleteExam(Long examId) {
        Exam exam = new Exam();
        exam.setExamId(examId);
        examDAO.delete(exam);
    }

    @Override
    public List<Exam> getAllExams() {
        return examDAO.findAllExams();
    }

    @Override
    public List<Question> getQuestionsOfExam(Long examId) {
        return examDAO.getAllQuestion(examId);
    }

    @Override
    public int calculateScore(Long examId, String[] answers) {
        int score=0;
        int i=0;
        for(Question q : examDAO.getAllQuestion(examId)){
            System.out.println("calculate Score method "+q+"answer "+answers[i]);
            if(q.getAnswer().equals(answers[i])){
                score+=1;
            }
            i++;
        }
        return score;

    }


//    public List<Question> getQuestionsOfExam(Long examId) {
//    }




}
