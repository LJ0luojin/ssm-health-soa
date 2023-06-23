package com.lh.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lh.dao.MemberDao;
import com.lh.dao.OrderDao;
import com.lh.dao.OrderSettingDao;
import com.lh.pojo.Member;
import com.lh.pojo.Order;
import com.lh.pojo.OrderSetting;
import com.lh.service.OrderService;
import com.lh.utils.DateUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderSettingDao orderSettingDao;
    @Resource
    private MemberDao memberDao;
    @Resource
    private OrderDao orderDao;
    @Override
    public Integer submit(Map<String, String> dataMap) throws Exception {
    //  1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
        String orderDate = dataMap.get("orderDate");
        String telephone = dataMap.get("telephone");
        Date date = DateUtils.parseString2Date(orderDate);//预约时间
        Order order = null;
        List<OrderSetting> orderSettingByMonth = orderSettingDao.getOrderSettingByMonth(orderDate);
        //如果返回值大于0，则说明要预约的那天，处于已设置的状态
        if(orderSettingByMonth.size()<=0 || orderSettingByMonth==null){
            return null;
        }

    //  2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
        OrderSetting orderSetting = orderSettingByMonth.get(0);
        if(orderSetting.getNumber()<=orderSetting.getReservations()){
            return null;
        }
    //  检查当前用户是否为会员
        Member member = memberDao.findByTelephone(telephone);
        //如果当前用户非会员，则自动注册成会员
        if(member==null){
            member = new Member();
            member.setName((String) dataMap.get("name"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String) dataMap.get("idCard"));
            member.setSex((String) dataMap.get("sex"));
            member.setRegTime(new Date());
            memberDao.add(member);
        }else{ //如果是会员，则判断该用户是否重复预约
            order = new Order();
            order.setMemberId(member.getId());
            order.setSetmealId(Integer.parseInt(dataMap.get("setmealId")));
            order.setOrderDate(date);
            List<Order> byCondition = orderDao.findByCondition(order);
            if(byCondition.size()>0){
                return null;
            }
            //如果没有重复预约，则将该次预约添加进Order表中
            order.setOrderType(Order.ORDERTYPE_WEIXIN);
            order.setOrderStatus(Order.ORDERSTATUS_NO);
            orderDao.add(order);
        }
        //  预约成功，更新当日的已预约人数
        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingDao.update(orderSetting);
        return order.getId();
    }
}
