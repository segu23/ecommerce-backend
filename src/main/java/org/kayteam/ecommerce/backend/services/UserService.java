package org.kayteam.licenses.services;

import org.kayteam.licenses.entities.User;
import org.kayteam.licenses.repositories.UserRepository;
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
