package com.csye6220.esdfinalproject.controller;

import com.csye6220.esdfinalproject.model.User;
import com.csye6220.esdfinalproject.model.UserRole;
import com.csye6220.esdfinalproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
public class SigninController {


    @Autowired
    UserService userService;
    @GetMapping("/signin")
    public ModelAndView AuthenticateLoginPage(){
        return new ModelAndView("signin");
    }

    @PostMapping("/login-success")
    public ModelAndView performValidation(@RequestParam String username, @RequestParam String password, HttpServletRequest request){

        ModelAndView modelAndView = new ModelAndView();

        User currentuser = userService.getUserByusername(username);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


        if(currentuser == null){
            modelAndView.setViewName("redirect:/signin");
            modelAndView.addObject("error", "Invalid User name!!!");
        }
        else {
            if (bCryptPasswordEncoder.matches(password, currentuser.getPassword())) {
                Set<UserRole> roles = currentuser.getUserRoles();
                System.out.println(roles);

                if (roles != null && (roles.contains(UserRole.ADMIN) || roles.contains(UserRole.STUDENT) || roles.contains(UserRole.INSTRUCTOR))) {
                    // Redirect to the homepage for all user roles
                    modelAndView.setViewName("redirect:/homepage");
                } else {
                    // Redirect to a default homepage or handle the case where roles are not present
                    modelAndView.setViewName("redirect:/access_denied");
                }

                System.out.println(roles);
                request.getSession().setAttribute("user", currentuser);
            }
            else {
                modelAndView.setViewName("redirect:/signin");
                modelAndView.addObject("error", "Invalid Password!!!!");
            }
        }
        return modelAndView;
    }
}
