package com.lh.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lh.constant.MessageConstant;
import com.lh.constant.RedisConstant;
import com.lh.entity.PageResult;
import com.lh.entity.QueryPageBean;
import com.lh.entity.Result;
import com.lh.pojo.Setmeal;
import com.lh.service.SetmealService;
import com.lh.utils.QiniuUtils;
import org.aspectj.bridge.Message;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Resource
    private JedisPool jedisPool;
    @Reference
    SetmealService setmealService;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile")MultipartFile file){

        try{
            //获取源文件名
            String originalFilename = file.getOriginalFilename();
            //获取文件后缀
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            //获取UUID生成随机文件名
            String fileName = UUID.randomUUID().toString() + suffix;
            //将上传的图片存入redis
            Jedis jedis = jedisPool.getResource();
            jedis.sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            //上传七牛云
            QiniuUtils.upload2Qiniu(file.getBytes(),fileName);
            Result result = new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
            result.setData(fileName);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            //图片上传失败
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }

    }

    @PostMapping("add")
    public Result add(@RequestBody Setmeal setmeal,@RequestParam("id") Integer[] checkgroupIds){
        //add t_setmeal表
        return setmealService.addSetmeal(setmeal,checkgroupIds);
    }

    @PostMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return setmealService.findPage(queryPageBean);
    }
}
