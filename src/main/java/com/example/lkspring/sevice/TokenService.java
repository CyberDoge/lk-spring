package com.example.lkspring.sevice;

import com.example.lkspring.model.Token;
import com.example.lkspring.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("tokenService")
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    public Integer findUserIdByConfirmationToken(String token) {
        return tokenRepository.findTokenByConfirmationToken(token).getUserId();
    }

    public void delete(Integer userId) {
        tokenRepository.deleteByUserId(userId);
    }

    public void save(Integer userId, String token) {
        Token t = new Token();
        t.setUserId(userId);
        t.setConfirmationToken(token);
        tokenRepository.save(t);
    }
}
