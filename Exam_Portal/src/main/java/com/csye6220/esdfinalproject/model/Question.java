package com.csye6220.esdfinalproject.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Question")
public class Question {

   @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long questionId;
    @Column(length = 5000)
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answer;
    @Transient
    private  String givenAnswer;

    @ManyToOne(fetch = FetchType.EAGER)
    private Exam exam;

 public Question() {
  this.exam = new Exam();
 }

 public long getQuestionId() {
  return questionId;
 }

 public void setQuestionId(long questionId) {
  this.questionId = questionId;
 }


 public String getQuestion() {
  return question;
 }

 public void setQuestion(String question) {
  this.question = question;
 }

 public String getOption1() {
  return option1;
 }

 public void setOption1(String option1) {
  this.option1 = option1;
 }

 public String getOption2() {
  return option2;
 }

 public void setOption2(String option2) {
  this.option2 = option2;
 }

 public String getOption3() {
  return option3;
 }

 public void setOption3(String option3) {
  this.option3 = option3;
 }

 public String getOption4() {
  return option4;
 }

 public void setOption4(String option4) {
  this.option4 = option4;
 }

 public String getAnswer() {
  return answer;
 }

 public void setAnswer(String answer) {
  this.answer = answer;
 }

 public String getGivenAnswer() {
  return givenAnswer;
 }

 public void setGivenAnswer(String givenAnswer) {
  this.givenAnswer = givenAnswer;
 }

 public Exam getExam() {
  return exam;
 }

 public void setExam(Exam exam) {
  this.exam = exam;
 }

 @Override
 public String toString() {
  return "Question{" +
          "questionId=" + questionId +
          ", question='" + question + '\'' +
          ", option1='" + option1 + '\'' +
          ", option2='" + option2 + '\'' +
          ", option3='" + option3 + '\'' +
          ", option4='" + option4 + '\'' +
          ", answer='" + answer + '\'' +
          ", givenAnswer='" + givenAnswer + '\'' +
          ", exam=" + exam +
          '}';
 }
}
