package com.lh.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lh.entity.PageResult;
import com.lh.entity.QueryPageBean;
import com.lh.entity.Result;
import com.lh.pojo.CheckGroup;
import com.lh.service.CheckGroupService;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import javax.ws.rs.POST;

@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @Reference
    CheckGroupService checkGroupService;
    @PostMapping("add")
    public Result add(@RequestBody CheckGroup checkGroup, @RequestParam("id") Integer[] checkItemIds){
        return checkGroupService.add(checkGroup,checkItemIds);
    }
    @PostMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return checkGroupService.findPage(queryPageBean);
    }
    @GetMapping("findById")
    public Result findById(@RequestParam("id") Integer checkgroupId){
        return  checkGroupService.findById(checkgroupId);
    }

    /**
     *
     * @param checkgroupId
     * @return
     */
    @GetMapping("findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(@RequestParam("id") Integer checkgroupId){
        return checkGroupService.findCheckItemIdsByCheckGroupId(checkgroupId);
    }

    @PostMapping("edit")
    public Result edit(@RequestBody CheckGroup checkGroup,@RequestParam("id") Integer[] ids){
        return checkGroupService.edit(checkGroup,ids);
    }

    @PostMapping("del")
    public Result del(@RequestParam("id") Integer id){
        return checkGroupService.del(id);
    }
    @PostMapping("findAll")
    public Result findAll(){
        return checkGroupService.findAll();
    }
}
