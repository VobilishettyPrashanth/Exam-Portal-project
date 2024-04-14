package com.csye6220.esdfinalproject.controller;



import com.csye6220.esdfinalproject.model.User;
import com.csye6220.esdfinalproject.model.UserRole;
import com.csye6220.esdfinalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class HomePageController {


    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ModelAndView Indexview(){
        ModelAndView mv1 = new ModelAndView("index");
        return mv1;
    }

    @GetMapping("/homepage")
    public ModelAndView HomepageHandler(){
        ModelAndView mv = new ModelAndView("homepage");

        userService.addUser(new User("prashanth", "vobilishetty","Prashanth Prashu", new BCryptPasswordEncoder().encode("prashu123"),"prashanthv@gmail.com",UserRole.STUDENT," Graduate STUDENT","9959931848" ));

        mv.addObject("Student_Count",userService.getUserCount(UserRole.STUDENT));
        mv.addObject("ADMIN_Count",userService.getUserCount(UserRole.ADMIN));
        mv.addObject("INSTRUCTOR_Count",userService.getUserCount(UserRole.INSTRUCTOR));
        mv.addObject("GUEST_Count",userService.getUserCount(UserRole.GUEST));

        return mv;
    }
}
