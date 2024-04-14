package com.csye6220.esdfinalproject.controller;

import com.csye6220.esdfinalproject.model.*;
import com.csye6220.esdfinalproject.service.CategoryService;
import com.csye6220.esdfinalproject.service.ExamService;
import com.csye6220.esdfinalproject.service.QuestionService;
import com.csye6220.esdfinalproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import java.beans.PropertyEditorSupport;

@RestController
@CrossOrigin("*")
@RequestMapping("/questions")
public class QuestionsController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ExamService examService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ModelAndView ViewQuestionsHandler(HttpServletRequest request) {
        User user = userService.getUserById(((User) request.getSession().getAttribute("user")).getId());

        User userInSession = (User) request.getSession().getAttribute("user");

        try {
            if (Set.of(UserRole.ADMIN, UserRole.INSTRUCTOR).contains(userInSession.getRole())) {
            ModelAndView mv = new ModelAndView("questions");

            List<Question> questions = questionService.getAllQuestions();

            mv.addObject("questions", questions);

            return mv;
        }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ModelAndView("access_denied");
    }
    @GetMapping("/create")
    public ModelAndView CreateQuestionHandler(HttpServletRequest request){

        User userInSession = (User) request.getSession().getAttribute("user");

        try {
            if (Set.of(UserRole.ADMIN, UserRole.INSTRUCTOR).contains(userInSession.getRole())) {
                System.out.println("h");
                Question quesData = new Question();
                Exam exam = new Exam();
                System.out.println("e");
                System.out.println();
                quesData.setExam(exam);

                List<Exam> exams = examService.getAllExams();
                ModelAndView mv = new ModelAndView("create_question");
                mv.addObject("quesData", quesData);
                mv.addObject("exams",exams);
                mv.addObject("user", userService.getUserById(userInSession.getId()));
                return mv;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ModelAndView("access_denied");
    }
    @PostMapping("/create")
    public ModelAndView QuestionCreation(HttpServletRequest request,
                                         @ModelAttribute("examData") Question quesData
    ){
        System.out.println("Question : "+ quesData.getQuestion());
        System.out.println("Question Id : " + quesData.getQuestionId());
        System.out.println("Answer : "+quesData.getAnswer());
        System.out.println(" Exam Id : "+quesData.getExam().getExamId());

        questionService.addQuestion(quesData);
        User current_user = userService.getUserById(((User)request.getSession().getAttribute("user")).getId());
        ModelAndView mv = new ModelAndView("question_success");
        mv.addObject("message", "Question created successfully!");
        return mv;
    }

    @GetMapping("/update/{questionId}")
    public ModelAndView updateQuestionHandler(HttpServletRequest request, @PathVariable Long questionId) {
        User userInSession = (User) request.getSession().getAttribute("user");

        try {
            if (Set.of(UserRole.ADMIN, UserRole.INSTRUCTOR).contains(userInSession.getRole())) {
                Question quesData = questionService.getQuestionById(questionId);
                List<Exam> exams = examService.getAllExams();

                questionService.updateQuestion(quesData);
                ModelAndView mv = new ModelAndView("update_question");
                mv.addObject("quesData", quesData);
                mv.addObject("exams", exams);
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
                                       @ModelAttribute("quesData") Question updatedQuestion) {


        questionService.updateQuestion(updatedQuestion);

        ModelAndView mv = new ModelAndView("question_success");
        mv.addObject("message", "Question updated successfully!");
        return mv;
    }

    @GetMapping("/exam/{examId}")
    public List<String> getQuestionsOfExam(HttpServletRequest request,
                                           @PathVariable("examId") Long examId) {
        List<Question> questions_list = examService.getQuestionsOfExam(examId);
        List<String> question_content = new ArrayList<>();

        for (Question question : questions_list) {

            String question_1 = question.getQuestion();
            question_content.add(question_1);
        }
        return question_content;
    }
    @GetMapping("/{questionId}")
    public Question getQuestionById(@PathVariable("questionId") Long questionId) {
        return this.questionService.getQuestionById(questionId);
    }

    @DeleteMapping("{questionId}")
    public void deleteQuestionById(@PathVariable("questionId") Long questionId) {
        this.questionService.deleteQuestion(questionId);
    }














    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Exam.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                // For converting the String category ID to Category object
                Exam exam = examService.getExamById(Long.valueOf(text));
                setValue(exam);
            }
        });
    }




}
