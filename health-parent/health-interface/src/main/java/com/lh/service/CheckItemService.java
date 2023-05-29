package com.lh.service;

import com.lh.entity.PageResult;
import com.lh.entity.QueryPageBean;
import com.lh.pojo.CheckItem;

public interface CheckItemService {
    void add(CheckItem checkItem);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);
}
