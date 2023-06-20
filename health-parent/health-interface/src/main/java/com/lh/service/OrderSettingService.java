package com.lh.service;

import com.lh.pojo.OrderSetting;

import java.util.List;

public interface OrderSettingService {

    void add(List<OrderSetting> list);

    List<OrderSetting> getOrderSettingByMonth(String date);

    void editNumberByDate(OrderSetting orderSetting);

    Integer echoOrderSet(OrderSetting orderSetting);
}
