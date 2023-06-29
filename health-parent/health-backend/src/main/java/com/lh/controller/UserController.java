package com.lh.controller;

import com.lh.constant.MessageConstant;
import com.lh.entity.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/getUsername")
    public Result getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if(user==null){
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
        return new Result(true,MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
    }
}
