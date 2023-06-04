package com.lh.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lh.constant.MessageConstant;
import com.lh.entity.PageResult;
import com.lh.entity.QueryPageBean;
import com.lh.entity.Result;
import com.lh.pojo.CheckItem;
import com.lh.service.CheckItemService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference
    CheckItemService checkItemService;
    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        try {
            checkItemService.add(checkItem);
        }catch (Exception e){
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
    }
    @PostMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult page = checkItemService.findPage(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return page;
    }
    @GetMapping("/findAll")
    public Result findAll(){
        return checkItemService.findAll();
    }
    @GetMapping("delete")
    public Result deleteCheckItem(@RequestParam("id") Integer id){
        return checkItemService.deleteById(id);
    }

    @GetMapping("findById")
    public Result findById(@RequestParam("id") Integer id){
        return checkItemService.findById(id);
    }

    @PostMapping("edit")
    public Result edit(@RequestBody CheckItem checkItem){
        return checkItemService.edit(checkItem);
    }
}
