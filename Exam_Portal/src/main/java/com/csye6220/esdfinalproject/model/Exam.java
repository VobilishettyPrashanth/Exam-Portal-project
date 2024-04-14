package com.csye6220.esdfinalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Exam")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long examId;

    private String title;

    private String description;

    private String MaximumMarks;

    private String numberOfQuestions;

    private boolean active = false;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;



    @OneToMany(mappedBy = "exam",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Question> questionset; // = new HashSet<>();

    public List<Question> getQuestionset() {
        return questionset;
    }

    public void setQuestionset(List<Question> questionset) {
        this.questionset = questionset;
    }

    public Exam() {
        this.category = new Category();
        this.questionset = new ArrayList<>();
    }

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMaximumMarks() {
        return MaximumMarks;
    }

    public void setMaximumMarks(String maximumMarks) {
        MaximumMarks = maximumMarks;
    }

    public String getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(String numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

//    public Set<Question> getQuestionset() {
//        return questionset;
//    }

//    public void setQuestionset(Set<Question> questionset) {
//        this.questionset = questionset;
//    }

    @Override
    public String toString() {
        return "Exam{" +
                "examId=" + examId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", MaximumMarks='" + MaximumMarks + '\'' +
                ", numberOfQuestions='" + numberOfQuestions + '\'' +
                ", active=" + active +
                ", category=" + category +
                '}';
    }
}

