package com.csye6220.esdfinalproject.controller;


import com.csye6220.esdfinalproject.model.User;
import com.csye6220.esdfinalproject.model.UserRole;
import com.csye6220.esdfinalproject.service.UserService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@Validated
public class SignUpController {

    @Autowired
    UserService userService;

    @GetMapping("/signup")
    public String redirectToRegistrationPage(){
        return "signup";
    }

    @PostMapping("/register")
    public ModelAndView UserregistrationHandlerr(
            @RequestParam(name = "firstname") @Size(min = 2, message = "First Name should be at least 2 characters") String firstName,
            @RequestParam(name = "lastname") @Size(min = 2, message = "Last Name should be at least 2 characters") String lastName,
            @RequestParam(name = "username") String username,
            @RequestParam @Size(min = 8, max = 15, message = "Password length must be between 8 and 15 characters") String password,
            @RequestParam @Email(message = "Please enter an valid Email") String email,
            @RequestParam(name = "role") String role,
            @RequestParam(name = "profile") String profile,
            @RequestParam(name = "phone") String phone
    ){

        ModelAndView modelAndView = new ModelAndView();

        User existingUser = userService.getUserByusername(username);

        if(existingUser != null){
            modelAndView.setViewName("redirect:/signup");
            modelAndView.addObject("error", "email-exists");
            return modelAndView;
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = new User(firstName, lastName,username, bCryptPasswordEncoder.encode(password),email, UserRole.valueOf(role),phone,profile);

        userService.addUser(user);

        modelAndView.addObject("success", "registration-successful");
        modelAndView.setViewName("redirect:/signin");
        return modelAndView;
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView constraintViolationException(ConstraintViolationException ex){
        Set<String> errors = new HashSet<>();
        if(ex.getConstraintViolations() == null){
            errors.add(ex.getMessage());
        }
        else{
            errors = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
        }
        return new ModelAndView("error", "errors", errors);
    }
}
