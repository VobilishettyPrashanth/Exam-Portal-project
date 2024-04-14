package com.csye6220.esdfinalproject.service;

import com.csye6220.esdfinalproject.model.User;
import com.csye6220.esdfinalproject.model.UserRole;

import java.util.List;

public interface UserService {

    public void addUser(User user);

    public Long getUserCount(UserRole role);

    public void updateUser(User user);

    public void deleteUser(User user);

    public User getUserByusername(String username);

    public User getUserById(long id);

    public User getUserByEmail(String email);

    public void deleteUserById(long id);

    public void deleteUserByEmail(String email);

    public List<User> getAllUsers();

//    public UserRole getUserRoe(UserRole role);

}
