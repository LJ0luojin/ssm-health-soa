package com.lh.service;

import com.lh.entity.PageResult;
import com.lh.entity.QueryPageBean;
import com.lh.entity.Result;
import com.lh.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealService {
    Result addSetmeal(Setmeal setmeal,Integer[] checkgroupIds);

    PageResult findPage(QueryPageBean queryPageBean);
    List<Setmeal> getAll();

    Setmeal findById(Integer id);

    List<Map<String, Object>> countSetmealOrder();
}
