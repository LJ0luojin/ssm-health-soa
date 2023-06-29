package com.lh.service;

import com.lh.pojo.User;

public interface UserService {
    User findByUserName(String username);
}
