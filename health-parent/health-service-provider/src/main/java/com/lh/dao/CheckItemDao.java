package com.lh.dao;

import com.github.pagehelper.Page;
import com.lh.entity.QueryPageBean;
import com.lh.pojo.CheckItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CheckItemDao {
    int add(CheckItem checkItem);

    Page<CheckItem> selectByCondition(String queryString);
}
