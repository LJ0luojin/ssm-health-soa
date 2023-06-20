package com.lh.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lh.constant.MessageConstant;
import com.lh.entity.Result;
import com.lh.pojo.OrderSetting;
import com.lh.service.OrderSettingService;
import com.lh.utils.POIUtils;
import com.lh.utils.StringDateFormat;
import org.aspectj.bridge.Message;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.GET;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile")MultipartFile excelFile){
        try{
            List<String[]> strings = POIUtils.readExcel(excelFile);
            List<OrderSetting> list = new ArrayList<>();
            for (String[] string : strings) {
                OrderSetting temp = new OrderSetting();
                Date tempdate = new Date(string[0]);
                java.sql.Date date = new java.sql.Date(tempdate.getTime());
                temp.setOrderDate(date);
                temp.setNumber(Integer.valueOf(string[1]));
                list.add(temp);
            }
            orderSettingService.add(list);
            return new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        }catch (IOException e){
            return new Result(false,MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    @GetMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(@RequestParam("date") String date){
        //查询的时间格式 如2023-06
        try{
            String format = StringDateFormat.format(date);
            List<OrderSetting> list =  orderSettingService.getOrderSettingByMonth(format);
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        }catch (Exception e){
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    @PostMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        try{
            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        }catch (Exception e){
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }

    @PostMapping("/echoOrderSet")
    public Result echoOrderSet(@RequestBody OrderSetting orderSetting){
        try{
            Integer echoNumber = orderSettingService.echoOrderSet(orderSetting);
            return new Result(true,"数据回显成功",echoNumber);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"数据回显失败");
        }
    }
}
