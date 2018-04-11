package com.example.lkspring.sevice;


import com.example.lkspring.model.User;
import com.example.lkspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        //user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setMiddleAttempt(0d);
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }
}
