package com.lh.service;

import com.lh.entity.PageResult;
import com.lh.entity.QueryPageBean;
import com.lh.entity.Result;
import com.lh.pojo.Setmeal;

public interface SetmealService {
    Result addSetmeal(Setmeal setmeal,Integer[] checkgroupIds);

    PageResult findPage(QueryPageBean queryPageBean);
}
