package org.kayteam.licenses.services;

import org.kayteam.licenses.entities.TokenJWT;
import org.kayteam.licenses.entities.User;
import org.kayteam.licenses.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Transactional
    public TokenJWT findByToken(String token){
        return tokenRepository.findByToken(token);
    }

    @Transactional
    public TokenJWT save(TokenJWT token) {
        return tokenRepository.save(token);
    }

    @Transactional
    public List<TokenJWT> findAllValidTokenByUser(User user) {
        return tokenRepository.findAllValidTokenByUser(user);
    }
}
