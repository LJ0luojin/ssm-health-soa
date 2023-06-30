package com.lh.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lh.dao.MemberDao;
import com.lh.dao.OrderDao;
import com.lh.service.ReportService;
import com.lh.utils.DateUtils;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {
    @Resource
    MemberDao memberDao;
    @Resource
    OrderDao orderDao;
    @Override
    public Map<String, Object> getBusinessReportData() throws Exception {

        Map<String,Object> resultMap = new HashMap<>();

        //获取当前日期
        String reportDate = DateUtils.parseDate2String(new Date());
        resultMap.put("reportDate",reportDate);
        //todayNewMember 获取当日新增会员数
        Integer todayNewMember = memberDao.findMemberCountByDate(reportDate);
        resultMap.put("todayNewMember",todayNewMember);
        //获取总会员数 totalMember
        Integer totalMember = memberDao.findMemberTotalCount();
        resultMap.put("totalMember",totalMember);
        //获取本周新增会员数 thisWeekNewMember
        Date thisWeekMonday = DateUtils.getThisWeekMonday();
        String thisWeekFirstDay = DateUtils.parseDate2String(thisWeekMonday);
        Integer thisWeekNewMember = memberDao.findMemberCountAfterDate(thisWeekFirstDay);
        resultMap.put("thisWeekNewMember",thisWeekNewMember);
        //获取本月新增会员数 thisMonthNewMember
        Date firstDay4ThisMonth = DateUtils.getFirstDay4ThisMonth();
        String thisMonthFirstDay = DateUtils.parseDate2String(firstDay4ThisMonth);
        Integer thisMonthNewMember = memberDao.findMemberCountAfterDate(thisMonthFirstDay);
        resultMap.put("thisMonthNewMember",thisMonthNewMember);


        //获取今日预约 todayOrderNumber
        Integer todayOrderNumber = orderDao.findOrderCountByDate(reportDate);
        resultMap.put("todayOrderNumber",todayOrderNumber);
        //获取今日到诊数
        Integer todayVisitsNumber = orderDao.findVisitsCountByDate(reportDate);
        resultMap.put("todayVisitsNumber",todayVisitsNumber);
        //获取本周预约数 thisWeekOrderNumber
        Integer thisWeekOrderNumber = orderDao.findOrderCountAfterDate(thisWeekFirstDay);
        resultMap.put("thisWeekOrderNumber",thisWeekOrderNumber);
        //获取本周到诊数 thisWeekVisitsNumber
        Integer thisWeekVisitsNumber = orderDao.findVisitsCountAfterDate(thisWeekFirstDay);
        resultMap.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        //获取本月预约数 thisMonthOrderNumber
        Integer thisMonthOrderNumber = orderDao.findOrderCountAfterDate(thisMonthFirstDay);
        resultMap.put("thisMonthOrderNumber",thisMonthOrderNumber);
        //获取本月到珍数 thisMonthVisitsNumber
        Integer thisMonthVisitsNumber = orderDao.findVisitsCountAfterDate(thisMonthFirstDay);
        resultMap.put("thisMonthVisitsNumber",thisMonthVisitsNumber);

        //获取热门套餐数
        List<Map> hotSetmeal = orderDao.findHotSetmeal();
        resultMap.put("hotSetmeal",hotSetmeal);
        return resultMap;
    }
}
