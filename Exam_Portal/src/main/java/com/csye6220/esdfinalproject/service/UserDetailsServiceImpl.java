package com.csye6220.esdfinalproject.service;

import com.csye6220.esdfinalproject.dao.UserDAO;
import com.csye6220.esdfinalproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.userDAO.findByUsername(username);
        if(user== null){
            System.out.println(" user not found");
            throw new UsernameNotFoundException("User not Found");
        }
        return user;
    }
}
