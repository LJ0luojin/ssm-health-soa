package com.lh.dao;

import com.github.pagehelper.Page;
import com.lh.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
    void addSetmeal(Setmeal setmeal);

    void setSetmealAndCheckgroup(Map<String, Integer> map);

    Page<Setmeal> findPage(String value);

    List<Setmeal> getAll();

    Setmeal findById(Integer id);

    List<Map<String, Object>> countSetmealOrder();
}
