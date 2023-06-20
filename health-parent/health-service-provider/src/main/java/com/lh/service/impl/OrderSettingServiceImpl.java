package com.lh.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lh.constant.MessageConstant;
import com.lh.dao.OrderSettingDao;
import com.lh.pojo.OrderSetting;
import com.lh.service.OrderSettingService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderSettingServiceImpl implements OrderSettingService {
    @Resource
    private OrderSettingDao orderSettingDao;

    @Override
    public void add(List<OrderSetting> list) {
        try{
            if(list.size()>0 || list!=null){
                for (OrderSetting orderSetting : list) {
                    long point = orderSettingDao.findOrderSettingByDate(orderSetting.getOrderDate());
                    if(point>0){
                        orderSettingDao.update(orderSetting);
                    }else {
                        orderSettingDao.add(orderSetting);
                    }
                }
            }
        }catch (Exception e){
            throw new RuntimeException(MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    @Override
    public List<OrderSetting> getOrderSettingByMonth(String date) {
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(date);
        return list;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        long orderSettingByDate = orderSettingDao.findOrderSettingByDate(orderSetting.getOrderDate());
        //查看当前日期是否已设置基本信息
        //如果已被设置，则修改
        //否则新增
        if(orderSettingByDate>0){
            orderSettingDao.update(orderSetting);
        }else{
            orderSettingDao.add(orderSetting);
        }
    }

    @Override
    public Integer echoOrderSet(OrderSetting orderSetting) {
        Integer echoNumber =  orderSettingDao.findOrderSettingByDate2(orderSetting);
        return echoNumber;
    }
}
