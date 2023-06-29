package com.lh.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lh.dao.UserDao;
import com.lh.pojo.User;
import com.lh.service.UserService;

import javax.annotation.Resource;
@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserDao userDao;
    @Override
    public User findByUserName(String username) {
        //查出相关信息
        return userDao.findByUserName(username);
    }
}
