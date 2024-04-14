package com.csye6220.esdfinalproject.controller;

import com.csye6220.esdfinalproject.model.User;
import com.csye6220.esdfinalproject.service.UserDetailsServiceImpl;
import com.csye6220.esdfinalproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


// get user by username
    @GetMapping("/{username}")
    public User getUser(@PathVariable("username") String username){
        return this.userService.getUserByusername(username);
    }

    @GetMapping("/current-user")
    public User getCurrentUser(Principal principal) {
        return ((User) this.userDetailsService.loadUserByUsername(principal.getName()));
    }

//delete user by user ID
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id){
         this.userService.deleteUserById((id));
    }
}
