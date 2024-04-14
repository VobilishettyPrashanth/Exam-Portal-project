package com.csye6220.esdfinalproject.service;

import com.csye6220.esdfinalproject.dao.UserDAO;
import com.csye6220.esdfinalproject.model.User;
import com.csye6220.esdfinalproject.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserDAO userDAO;

    @Override
    public void addUser(User user) {
        userDAO.save(user);
    }

    @Override
    public Long getUserCount(UserRole role) {
       return userDAO.getUserCountByRole(role);
    }

    @Override
    public void updateUser(User user) {
        userDAO.update(user);
    }

    @Override
    public void deleteUser(User user) {
        userDAO.delete(user);
    }

    @Override
    public User getUserByusername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public User getUserById(long id) {
        return userDAO.getById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDAO.getByEmail(email);
    }

    @Override
    public void deleteUserById(long id) {
        userDAO.deleteById(id);
    }

    @Override
    public void deleteUserByEmail(String email) {
        userDAO.deleteByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

//    @Override
//    public UserRole getUserRoe(UserRole role) {
//        return userDAO.getUserRole();
//    }

}
