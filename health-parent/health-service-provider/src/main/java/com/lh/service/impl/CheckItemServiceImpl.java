package com.lh.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lh.dao.CheckItemDao;
import com.lh.pojo.CheckItem;
import com.lh.service.CheckItemService;

import javax.annotation.Resource;

@Service
public class CheckItemServiceImpl implements CheckItemService {
    @Resource
    CheckItemDao checkItemDao;
    @Override
    public void add(CheckItem checkItem) {
        int add = checkItemDao.add(checkItem);
        if(add==0){
            throw new RuntimeException("添加数据失败！");
        }
    }
}
