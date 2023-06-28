package com.lh.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lh.constant.MessageConstant;
import com.lh.constant.RedisConstant;
import com.lh.entity.Result;
import com.lh.pojo.Member;
import com.lh.service.MemberService;
import org.aspectj.bridge.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Resource
    JedisPool jedisPool;

    @Reference
    MemberService memberService;
    @PostMapping("/login")
    public Result login(@RequestBody Map<String,String> map, HttpServletResponse httpServletResponse){
        //        1、校验用户输入的短信验证码是否正确，如果验证码错误则登录失败
        String telephone = map.get("telephone");
        String validateCode = map.get("validateCode");
        String redisCode = jedisPool.getResource().get(RedisConstant.VALIDATECODE_LOGIN + telephone);
        if(!validateCode.equals(redisCode) || redisCode==null || validateCode==null){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        //        2、如果验证码正确，则判断当前用户是否为会员，如果不是会员则自动完成会员注册
        Member member = memberService.findByTelephone(telephone);
        if(member==null){
            member = new Member();
            member.setName(telephone);
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            memberService.add(member);
        }
        //        3、向客户端写入Cookie，内容为用户手机号
        String token = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("login_token", token);
        httpServletResponse.addCookie(cookie);
        //        4、将会员信息保存到Redis，使用手机号作为key，保存时长为30分钟
        jedisPool.getResource().setex(RedisConstant.TOKEN_LOGIN+telephone,30*60,token);
        return new Result(true,MessageConstant.LOGIN_SUCCESS);
    }
}
