package com.lh.service;

import com.lh.entity.PageResult;
import com.lh.entity.QueryPageBean;
import com.lh.entity.Result;
import com.lh.pojo.CheckItem;

public interface CheckItemService {
    void add(CheckItem checkItem);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    Result deleteById(int id);

    Result findById(Integer id);

    Result edit(CheckItem checkItem);

    Result findAll();
}
