package com.lh.dao;

import com.github.pagehelper.Page;
import com.lh.entity.QueryPageBean;
import com.lh.entity.Result;
import com.lh.pojo.CheckItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CheckItemDao {
    int add(CheckItem checkItem);

    Page<CheckItem> selectByCondition(String queryString);

    int deleteById(int id);

    Long findCountByCheckItemId(Integer id);

    CheckItem findById(Integer id);

    int edit(CheckItem checkItem);

    List<CheckItem> findAll();
}
