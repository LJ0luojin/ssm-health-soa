package com.lh.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lh.constant.MessageConstant;
import com.lh.entity.Result;
import com.lh.service.MemberService;
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

}
