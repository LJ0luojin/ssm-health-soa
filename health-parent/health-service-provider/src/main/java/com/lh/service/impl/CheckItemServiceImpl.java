package com.lh.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lh.constant.MessageConstant;
import com.lh.dao.CheckItemDao;
import com.lh.entity.PageResult;
import com.lh.entity.Result;
import com.lh.pojo.CheckItem;
import com.lh.service.CheckItemService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class CheckItemServiceImpl implements CheckItemService {
    @Resource
    CheckItemDao checkItemDao;
    @Override
    public void add(CheckItem checkItem) {
        int add = checkItemDao.add(checkItem);
        if(add==0){
            throw new RuntimeException("添加数据失败！");
        }
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> result =  checkItemDao.selectByCondition(queryString);
        return new PageResult(result.getTotal(),result.getResult());
    }

    @Override
    public Result deleteById(int id) {
          if (checkItemDao.findCountByCheckItemId(id)>0){
              return new Result(false,"当前检查项与某检查组绑定,无法单独删除该检查项");
          }
          if(checkItemDao.deleteById(id)==0){
              return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
          }else{
              return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
          }
    }

    @Override
    public Result findById(Integer id) {
        CheckItem byId = checkItemDao.findById(id);
        if(Objects.isNull(byId)){
            return new Result(false,"查询id:"+id+"的检查项失败");
        }
        return new Result(true,"查询id:"+id+"的检查项成功",byId);
    }

    @Override
    public Result edit(CheckItem checkItem) {
        int affectedRows = checkItemDao.edit(checkItem);
        if(affectedRows==0){
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    @Override
    public Result findAll() {
        List<CheckItem> checkItemsList = checkItemDao.findAll();
        if(checkItemsList!=null && checkItemsList.size() > 0){
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemsList);
        }else{
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }


}
