package com.lh.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lh.constant.MessageConstant;
import com.lh.dao.CheckGroupDao;
import com.lh.entity.PageResult;
import com.lh.entity.QueryPageBean;
import com.lh.entity.Result;
import com.lh.pojo.CheckGroup;
import com.lh.service.CheckGroupService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class CheckGroupServiceImpl implements CheckGroupService {
    @Resource
    CheckGroupDao checkGroupDao;

//    @Transactional
    @Override
    public Result add(CheckGroup checkGroup,Integer[] checkItemIds) {
        Integer add = checkGroupDao.add(checkGroup);
        if(add==0 || add == null){
            return new Result(false,MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        setCheckGroupAndCheckItem(checkGroup.getId(),checkItemIds);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<CheckGroup> result = checkGroupDao.selectByCondition(queryPageBean.getQueryString());
        if(result.size()!=0 && result !=null){
            return new PageResult(result.getTotal(),result.getResult());
        }
        return null;
    }

    @Override
    public Result findById(Integer checkgroupId) {
        CheckGroup checkGroup = checkGroupDao.findById(checkgroupId);
        if(checkGroup==null){
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
    }

    @Override
    public Result findCheckItemIdsByCheckGroupId(Integer checkgroupId) {
        List<Integer> result = checkGroupDao.findCheckItemIdsByCheckGroupId(checkgroupId);
        if(result==null || result.size()==0){
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,result);
    }
//    @Transactional
    @Override
    public Result edit(CheckGroup checkGroup, Integer[] ids) {
        try{
            checkGroupDao.deleteAssociation(checkGroup.getId());
            checkGroupDao.edit(checkGroup);
            setCheckGroupAndCheckItem(checkGroup.getId(),ids);
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }

    @Override
    public Result del(Integer id) {
        try{
            //删除检查组，首先要删除t_checkgroup_checkitem中关联的数据
            checkGroupDao.deleteAssociation(id);
            //然后删除t_checkgroup中对应的数据
            checkGroupDao.delelteCheckGroupById(id);
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    @Override
    public Result findAll() {
        try{
            List<CheckGroup> list = checkGroupDao.findAll();
            if(list==null){
                return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
            }
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }


    public void setCheckGroupAndCheckItem(Integer checkGroupId,Integer[] checkItemIds){
        Map<String,Integer> map = new HashMap<>();
        if(checkItemIds.length !=0 && checkItemIds !=null){
            for (Integer checkItemId : checkItemIds) {
                map.put("checkgroup_id",checkGroupId);
                map.put("checkitem_id",checkItemId);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }

    }
}
