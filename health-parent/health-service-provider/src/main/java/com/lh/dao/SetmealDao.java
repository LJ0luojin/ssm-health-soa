package com.lh.dao;

import com.github.pagehelper.Page;
import com.lh.pojo.Setmeal;

import java.util.Map;

public interface SetmealDao {
    void addSetmeal(Setmeal setmeal);

    void setSetmealAndCheckgroup(Map<String, Integer> map);

    Page<Setmeal> findPage(String value);
}
