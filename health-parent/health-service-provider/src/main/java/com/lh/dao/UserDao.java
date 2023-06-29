package com.lh.dao;

import com.lh.pojo.User;

public interface UserDao {
    User findByUserName(String username);
}
