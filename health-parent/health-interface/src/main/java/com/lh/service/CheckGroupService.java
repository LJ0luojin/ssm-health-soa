package com.lh.service;

import com.lh.entity.PageResult;
import com.lh.entity.QueryPageBean;
import com.lh.entity.Result;
import com.lh.pojo.CheckGroup;

public interface CheckGroupService {
    Result add(CheckGroup checkGroup,Integer[] checkItemIds);

    PageResult findPage(QueryPageBean queryPageBean);

    Result findById(Integer checkgroupId);

    Result findCheckItemIdsByCheckGroupId(Integer checkgroupId);

    Result edit(CheckGroup checkGroup, Integer[] ids);

    Result del(Integer id);

    Result findAll();
}
