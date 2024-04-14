package com.csye6220.esdfinalproject.controller;

import com.csye6220.esdfinalproject.model.*;
import com.csye6220.esdfinalproject.service.CategoryService;
import com.csye6220.esdfinalproject.service.ExamService;
import com.csye6220.esdfinalproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.beans.PropertyEditorSupport;
import java.util.*;

@Controller
@RequestMapping("/exams")
@Validated
public class ExamController {
    @Autowired
    private ExamService examService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private HttpServletRequest request;


    @GetMapping
    public ModelAndView viewExamsHandler(HttpServletRequest request) {

        User user = userService.getUserById(((User) request.getSession().getAttribute("user")).getId());

        ModelAndView mv = new ModelAndView("exams");

        List<Exam> examCategories =examService.getAllExams();

        mv.addObject("exams", examCategories);

        return mv;
    }
    @GetMapping("/create")
    public ModelAndView CreatExamHandler(HttpServletRequest request){

        User userInSession = (User) request.getSession().getAttribute("user");

        try {
            if (Set.of(UserRole.ADMIN, UserRole.INSTRUCTOR).contains(userInSession.getRole())) {

                Exam examData = new Exam();
                examData.setCategory(new Category());
                List<Category> categories = categoryService.getAllCategories();
                ModelAndView mv = new ModelAndView("create_exam");
                mv.addObject("examData", examData);
                mv.addObject("categories",categories);
                mv.addObject("user", userService.getUserById(userInSession.getId()));
                return mv;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ModelAndView("access_denied");
    }
    //To add Exam Category
    @PostMapping("/create")
    public ModelAndView CreateNewExam(HttpServletRequest request,@ModelAttribute("examData") Exam examData)
    {
        System.out.println("Title : "+ examData.getTitle());
        System.out.println("Maximum Marks: " + examData.getMaximumMarks());
        System.out.println("Description : "+examData.getDescription());
        System.out.println("Category Id : "+examData.getCategory().getCategoryId());
        System.out.println("Number of Questions: " + examData.getNumberOfQuestions());
        System.out.println("Active: " + examData.isActive());

        examService.addExam(examData);
        User current_user = userService.getUserById(((User)request.getSession().getAttribute("user")).getId());

        ModelAndView mv = new ModelAndView("exam_success");
        mv.addObject("message", "Exam created successfully!");
        return mv;
    }

    @GetMapping("/update/{examId}")
    public ModelAndView updateExamHandler(HttpServletRequest request, @PathVariable Long examId) {

        User userInSession = (User) request.getSession().getAttribute("user");

        try {
            if (Set.of(UserRole.ADMIN, UserRole.INSTRUCTOR).contains(userInSession.getRole())) {
                Exam examData = examService.getExamById(examId);
                List<Category> categories = categoryService.getAllCategories();

                examService.updateExam(examData);
                ModelAndView mv = new ModelAndView("update_exam");
                mv.addObject("examData", examData);
                mv.addObject("categories", categories);
                mv.addObject("user", userService.getUserById(userInSession.getId()));
                return mv;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new ModelAndView("access_denied");
    }

    @PostMapping("/update")
    public ModelAndView updateQuestion(HttpServletRequest request,
                                       @ModelAttribute("examData") Exam updatedExam) {

        examService.updateExam(updatedExam);

        ModelAndView mv = new ModelAndView("exam_success");
        mv.addObject("message", "Exam updated successfully!");
        return mv;
    }

    // To extract single exam
    @GetMapping("/{examId}")
    public ModelAndView getQuestionsOfExam(@PathVariable("examId") Long examId){

        User userInSession = (User) request.getSession().getAttribute("user");
        return new ModelAndView(String.valueOf(this.examService.getExamById(examId)));

    }

    @GetMapping("/take")
    public  ModelAndView GetAllExams(HttpServletRequest request){
        User user = userService.getUserById(((User) request.getSession().getAttribute("user")).getId());
        ModelAndView mv = new ModelAndView("take_exam");
        List<Exam> available_exams =examService.getAllExams();
        mv.addObject("exams", available_exams);
        return mv;
    }

    @GetMapping("/take/{examId}")
    public ModelAndView TakeExamHandler(HttpServletRequest request, @PathVariable("examId")Long examId){
        User userInSession = (User) request.getSession().getAttribute("user");
        try {
            if (Set.of(UserRole.ADMIN, UserRole.INSTRUCTOR, UserRole.STUDENT).contains(userInSession.getRole())) {
               List<Question> questions = examService.getQuestionsOfExam(examId);
                System.out.println(questions);
               for(Question q : questions){
                   System.out.println(q);
               }
               ModelAndView mv = new ModelAndView("attempt_exam");
               mv.addObject("questions",questions);
               mv.addObject("examId",examId);
               return mv;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ModelAndView("access_denied");
    }

    @PostMapping("{examId}/submitted")
    public ModelAndView ExamSubmissionHandler(@PathVariable("examId") Long examId, HttpServletRequest request, @ModelAttribute("questions") Question questions
        ) {

        System.out.println(questions);
        String[] answers = questions.getGivenAnswer().split(",");
        System.out.println(Arrays.toString(answers));
        int score = examService.calculateScore(examId,answers);

        System.out.println(score);
        ModelAndView mv = new ModelAndView("exam_success");
        mv.addObject("message", "Exam submitted successfully!");
        mv.addObject("score",score);
        return mv;
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Category.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                // Convert the String category ID to Category object
                Category category = categoryService.getCategoryById(Long.valueOf(text));
                setValue(category);
            }
        });
    }

}
