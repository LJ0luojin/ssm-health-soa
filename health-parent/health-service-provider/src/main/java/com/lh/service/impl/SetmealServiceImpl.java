package com.lh.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lh.constant.MessageConstant;
import com.lh.constant.RedisConstant;
import com.lh.dao.SetmealDao;
import com.lh.entity.PageResult;
import com.lh.entity.QueryPageBean;
import com.lh.entity.Result;
import com.lh.pojo.Setmeal;
import com.lh.service.SetmealService;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class SetmealServiceImpl implements SetmealService {
    @Resource
    JedisPool jedisPool;
    @Resource
    SetmealDao setmealDao;
    @Override
    public Result addSetmeal(Setmeal setmeal, Integer[] checkgroupIds) {
        try{
            //添加setmeal表
            setmealDao.addSetmeal(setmeal);
            setSetmealAndCheckgroup(setmeal.getId(),checkgroupIds);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        }catch (Exception e){
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Setmeal> list = setmealDao.findPage(queryPageBean.getQueryString());
        return new PageResult(list.getTotal(),list.getResult());
    }

    public  void setSetmealAndCheckgroup(Integer setmealId,Integer[] checkgroupIds){
        if(checkgroupIds.length>0){
            Map<String,Integer> map = new HashMap<>();
            for (Integer checkgroupId : checkgroupIds) {
                map.put("setmeal_id",setmealId);
                map.put("checkgroup_id",checkgroupId);
                setmealDao.setSetmealAndCheckgroup(map);
            }
        }
    }
}
