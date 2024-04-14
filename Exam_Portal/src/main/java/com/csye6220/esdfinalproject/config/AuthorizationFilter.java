package com.csye6220.esdfinalproject.config;

import com.csye6220.esdfinalproject.model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.util.*;

@Component
@Order(1)
public class AuthorizationFilter implements Filter {

    private static List<String> allowedURLs = List.of("/", "/signin", "/signout", "/login-success", "/signup", "/register","/categories/**","/homepage/**","exams/**","questions/**","/user/**");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        User user = (User) request.getSession().getAttribute("user");
        String requestURI = request.getRequestURI();
        System.out.println("Request URI in filter: " + requestURI);
        if(!allowedURLs.contains(requestURI)){
            if(user == null){
                System.out.println("r");
                response.sendRedirect("/signin");
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}