package com.csye6220.esdfinalproject.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignoutController {

    @GetMapping("/signout")
    public String AuthenticateSignOut(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/index";
    }
}
