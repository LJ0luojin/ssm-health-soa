package com.lh.controller;

import com.lh.constant.MessageConstant;
import com.lh.constant.RedisConstant;
import com.lh.entity.Result;
import com.lh.utils.SMSUtils;
import com.lh.utils.ValidateCodeUtils;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Resource
    JedisPool jedisPool;

    @PostMapping("/send4Order")
    public Result send4Order(@RequestParam("telephone") String telephone){
        try{
            String mobileRegEx = "^1[3,4,5,6,7,8,9][0-9]{9}$";
            //校验手机号
            Pattern pattern = Pattern.compile(mobileRegEx);//函数语法 匹配的正则表达式
            Matcher matcher = pattern.matcher(telephone);//进行匹配
            if(!matcher.matches()){
                return new Result(false,"您输入的手机号不合法，请检查后重新输入!");
            }
            //发送短信（生成随机验证码，调用短信接口，将验证码存入redis）
            //生成验证码
            String validateCode = String.valueOf(ValidateCodeUtils.generateValidateCode(4));
            //调用短信接口
            System.out.println(validateCode);
//            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,validateCode);
            //将验证码存入redis
            Jedis jedis = jedisPool.getResource();
            jedis.setex(RedisConstant.VALIDATECODE_ORDER+":"+telephone,300,validateCode);
            return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
        }catch (Exception e){
            //logger.error
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

    @PostMapping("/send4Login")
    public Result send4Login(@RequestParam("telephone") String telephone){
        try{
            String mobileRegEx = "^1[3,4,5,6,7,8,9][0-9]{9}$";
            //校验手机号
            Pattern pattern = Pattern.compile(mobileRegEx);//函数语法 匹配的正则表达式
            Matcher matcher = pattern.matcher(telephone);//进行匹配
            if(!matcher.matches()){
                return new Result(false,"您输入的手机号不合法，请检查后重新输入!");
            }
            //发送短信（生成随机验证码，调用短信接口，将验证码存入redis）
            //生成验证码
            String validateCode = String.valueOf(ValidateCodeUtils.generateValidateCode(6));
            //调用短信接口
            System.out.println(validateCode);
//            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,validateCode);
            //将验证码存入redis
            Jedis jedis = jedisPool.getResource();
            jedis.setex(RedisConstant.VALIDATECODE_LOGIN+telephone,300,validateCode);
            return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
        }catch (Exception e){
            //logger.error
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}
