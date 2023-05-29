package com.lh.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lh.dao.CheckItemDao;
import com.lh.entity.PageResult;
import com.lh.pojo.CheckItem;
import com.lh.service.CheckItemService;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> result =  checkItemDao.selectByCondition(queryString);
        return new PageResult(result.getTotal(),result.getResult());
    }

}
