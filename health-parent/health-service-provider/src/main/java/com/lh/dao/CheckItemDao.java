package com.lh.dao;

import com.lh.pojo.CheckItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CheckItemDao {
    int add(CheckItem checkItem);
}
