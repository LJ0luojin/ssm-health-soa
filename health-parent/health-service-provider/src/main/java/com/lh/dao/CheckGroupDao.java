package com.lh.dao;

import com.github.pagehelper.Page;
import com.lh.pojo.CheckGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface CheckGroupDao {

    Integer add(CheckGroup checkGroup);
    void setCheckGroupAndCheckItem(Map<String, Integer> map);

    Page<CheckGroup> selectByCondition(String value);

    CheckGroup findById(Integer checkgroupId);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer checkgroupId);

    void deleteAssociation(Integer id);

    void edit(CheckGroup checkGroup);

    void delelteCheckGroupById(Integer id);

    List<CheckGroup> findAll();

    CheckGroup findCheckGruopBySetmealId(Integer id);
}
