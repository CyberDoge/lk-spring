package com.example.lkspring.sevice;


import com.example.lkspring.model.User;

public interface UserService {
    void save(User user);
    void update(User user);
    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);
    User findByEmail(String email);
    User findByConfirmationToken(String token);
}
