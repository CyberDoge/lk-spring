package com.example.lkspring.repository;

import com.example.lkspring.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository("tokenRepository")
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Token findTokenByUserId(Integer userId);
    Token findTokenByConfirmationToken(String token);
    @Transactional
    void deleteByUserId(Integer userId);
}
