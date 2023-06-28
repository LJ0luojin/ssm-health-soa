package com.lh.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lh.constant.MessageConstant;
import com.lh.constant.RedisConstant;
import com.lh.dao.CheckGroupDao;
import com.lh.dao.SetmealDao;
import com.lh.entity.PageResult;
import com.lh.entity.QueryPageBean;
import com.lh.entity.Result;
import com.lh.pojo.Setmeal;
import com.lh.service.SetmealService;
import com.lh.utils.GenerateStaticHtmlUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SetmealServiceImpl implements SetmealService {
    @Resource
    JedisPool jedisPool;
    @Resource
    SetmealDao setmealDao;
    @Resource
    CheckGroupDao checkGroupDao;
    @Resource
    FreeMarkerConfigurer freemarkerConfig;
    @Value("${out_put_path}")
    String outPutPath;
    @Override
    public Result addSetmeal(Setmeal setmeal, Integer[] checkgroupIds) {
        try{
            //添加setmeal表
            setmealDao.addSetmeal(setmeal);
            setSetmealAndCheckgroup(setmeal.getId(),checkgroupIds);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
            //生成静态化页面
            generateMobileStaticHtml();
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        }catch (Exception e){
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }
    //生成静态化页面
    private void generateMobileStaticHtml() {
        //获取所有套餐
        List<Setmeal> setmealList = getAll();
        //1 生成套餐列表静态化页面
        generateSetmealListHtml(setmealList);
        //2 生成套餐详情页静态化页面
        generateSetmealDetailHtml(setmealList);

    }

    private void generateSetmealDetailHtml(List<Setmeal> setmealList) {
        Map<String,Object> dataMap = new HashMap<>();
        for (Setmeal setmeal : setmealList) {
            Setmeal s = findById(setmeal.getId());
            dataMap.put("setmeal",s);
            GenerateStaticHtmlUtils.baseGenerateStaticHtml(freemarkerConfig,
                    "mobile_setmeal_detail.ftl",dataMap,outPutPath,"setmeal_detail_"+s.getId()+".html");
        }
    }

    private void generateSetmealListHtml(List<Setmeal> setmealList) {
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("setmealList",setmealList);
        GenerateStaticHtmlUtils.baseGenerateStaticHtml(freemarkerConfig,
                "mobile_setmeal.ftl",dataMap,outPutPath,"m_setmeal.html");
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Setmeal> list = setmealDao.findPage(queryPageBean.getQueryString());
        return new PageResult(list.getTotal(),list.getResult());
    }

    @Override
    public List<Setmeal> getAll() {
        return setmealDao.getAll();
    }

    @Override
    public Setmeal findById(Integer id) {
        Setmeal result = setmealDao.findById(id);
        return result;
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
