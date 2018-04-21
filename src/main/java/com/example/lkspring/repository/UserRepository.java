package com.example.lkspring.repository;

import com.example.lkspring.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);
    User findByEmail(String email);
}
