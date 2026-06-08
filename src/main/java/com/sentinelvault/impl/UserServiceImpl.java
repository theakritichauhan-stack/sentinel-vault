package com.sentinelvault.impl;

import com.sentinelvault.entity.User;
import com.sentinelvault.repository.UserRepository;
import com.sentinelvault.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User register(User user) {

        return userRepository.save(user);
    }

    @Override
    public User login(String username, String password) {

        User user =
                userRepository.findByUsername(username);

        if(user != null &&
                user.getPassword().equals(password)) {

            return user;
        }

        return null;
    }
}