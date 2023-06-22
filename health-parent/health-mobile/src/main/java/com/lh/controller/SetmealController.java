package com.lh.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lh.constant.MessageConstant;
import com.lh.entity.Result;
import com.lh.pojo.Setmeal;
import com.lh.service.SetmealService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;

    @PostMapping("/getAllSetmeal")
    public Result getAllSetmeal(){
        try{
            List<Setmeal> list = setmealService.getAll();
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }

    @PostMapping("/findById")
    public Result findById(@RequestParam("id")Integer id){
        try {
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

}
