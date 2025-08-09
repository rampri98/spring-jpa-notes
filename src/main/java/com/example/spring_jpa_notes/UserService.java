package com.example.spring_jpa_notes;

import com.example.spring_jpa_notes.UserRepository;
import com.example.spring_jpa_notes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void createUser(User user) {
        try {
            userRepository.save(user);
        } catch (DataAccessException ex) {
            System.err.println("Database error: " + ex.getMessage());
        }
    }
}