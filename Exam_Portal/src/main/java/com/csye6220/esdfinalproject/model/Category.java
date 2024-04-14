package com.csye6220.esdfinalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "Category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long categoryId;
    @jakarta.persistence.Column(name = "title")
    private String title;
    @jakarta.persistence.Column(name = "description")
    private String description;
    @jakarta.persistence.Column(name = "Category_Type")
    private CategoryTypes categoryTypes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Created_By")
    private User createdByUser;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Exam> examset = new LinkedHashSet<>();

    public Category() {
    }

    public Category(String title, String description) {
        this.title = title;
        this.description = description;
    }

//    public Category(String title, CategoryTypes currentType, String description, User currentUser) {
//    }


    public Category(String title, String description, CategoryTypes categoryTypes, User createdByUser) {
        this.title = title;
        this.description = description;
        this.categoryTypes = categoryTypes;
        this.createdByUser = createdByUser;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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

    public CategoryTypes getCategoryTypes() {
        return categoryTypes;
    }

    public void setCategoryTypes(CategoryTypes categoryTypes) {
        this.categoryTypes = categoryTypes;
    }
}
