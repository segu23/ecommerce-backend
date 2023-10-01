package org.kayteam.ecommerce.backend.services;

import org.kayteam.ecommerce.backend.models.User;
import org.kayteam.ecommerce.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User save(User user){
        return userRepository.save(user);
    }
}
