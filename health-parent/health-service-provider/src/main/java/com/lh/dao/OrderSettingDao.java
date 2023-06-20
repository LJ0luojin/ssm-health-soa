package com.lh.dao;

import com.lh.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    //添加预约设置
    void add(OrderSetting orderSetting);
    //检查日期是否已经存在
    long findOrderSettingByDate(Date date);
    //更新
    void update(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(String date);

    Integer findOrderSettingByDate2(OrderSetting orderSetting);
}
