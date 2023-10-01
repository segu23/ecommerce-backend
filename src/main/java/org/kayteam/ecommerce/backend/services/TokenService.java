package org.kayteam.ecommerce.backend.services;

import org.kayteam.ecommerce.backend.models.TokenJWT;
import org.kayteam.ecommerce.backend.models.User;
import org.kayteam.ecommerce.backend.repositories.TokenRepository;
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
