package com.lh.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lh.constant.MessageConstant;
import com.lh.entity.Result;
import com.lh.service.MemberService;
import com.lh.service.SetmealService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    MemberService memberService;
    @Reference
    SetmealService setmealService;
    @GetMapping("/getMemberReport")
    public Result getMemberReport(){
        Calendar date= Calendar.getInstance();
        date.set(Calendar.MONTH,date.get(Calendar.MONTH)-12);
        Map<String,Object> data = new HashMap<>();
        List<String> months = new ArrayList<>();
        List<Integer> memberCount = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for(int i = 0;i<11;i++){
            date.set(Calendar.MONTH,date.get(Calendar.MONTH)+1);
            String format = simpleDateFormat.format(date.getTime());
            String tempDate = format.substring(0, format.lastIndexOf("-"));//yyyy-MM
            format = tempDate+"-01";
            int count = memberService.countByRegTime(format);
            // 存入months
            months.add(tempDate);
            // 存入memberCount
            memberCount.add(count);
        }
        data.put("months",months);
        data.put("memberCount",memberCount);
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,data);
    }

    @GetMapping("/getSetmealReport")
    public Result getSetmealReport(){
        //从数据查数据
        //返回数据格式为 setmealNames：["",""]
        //            setmealCount:{["name":"value1","value":10...]}
        List<Map<String,Object>> datemap =  setmealService.countSetmealOrder();
        //存入数据
        Map<String,Object> resultMap = new HashMap<>();
        List<String> setmealNames = new ArrayList<>();
        for (Map<String, Object> map : datemap) {
            setmealNames.add((String) map.get("name"));
        }
        resultMap.put("setmealNames",setmealNames);
        resultMap.put("setmealCount",datemap);
        return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,resultMap);
    }

}
