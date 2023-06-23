package com.lh.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lh.constant.MessageConstant;
import com.lh.constant.RedisConstant;
import com.lh.entity.Result;
import com.lh.service.OrderService;
import com.lh.utils.SMSUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Map;

import static sun.misc.MessageUtils.where;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private JedisPool jedisPool;
    @Reference
    private OrderService orderService;
    @RequestMapping("/submit")
    public Result submit(@RequestBody Map<String,String> dataMap){
        try {
            //1校验数据
            //2校验通过之后，从redis中取出验证码进行验证码比对
            String redisValidate = jedisPool.getResource().get(RedisConstant.VALIDATECODE_ORDER + ":" + dataMap.get("telephone"));
            String dateMapValidate = dataMap.get("validateCode");
                //如果其中redis中验证码为空，则验证码过期。map中为空则未输入验证码。或者验证码比对结果为false
            if(redisValidate==null||dateMapValidate==null||!redisValidate.equals(dateMapValidate)){
                return new Result(false,MessageConstant.ORDER_FUIL);
            }
            //3比对通过后进行 service 操作
                //3-1 如果预约成功之后，要跳转到orderSuccess.html页面，且携带orderId
                Integer resultOrderId = orderService.submit(dataMap);
            //4成功后发送短信，通知用户，预约成功
                //短信存在发送成功、或不成功的情况

//            Integer flag = 3;
//            while (flag>0){
//                if(SMSUtils.sendShortMessageForOrderSuccess(SMSUtils.ORDER_NOTICE,dataMap.get("telephone"))){
//                    break;
//                }else{
//                    flag--;
//                }
//            }
            if(resultOrderId==null){
                return new Result(false, MessageConstant.ORDER_FUIL);
            }
            return new Result(true,MessageConstant.ORDER_SUCCESS);
        }catch (Exception e){
            //logger
            return new Result(false, MessageConstant.ORDER_FUIL);
        }
    }
}
