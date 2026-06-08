package com.sentinelvault.service;

import com.sentinelvault.entity.User;

public interface UserService {

    User register(User user);

    User login(String username, String password);
}