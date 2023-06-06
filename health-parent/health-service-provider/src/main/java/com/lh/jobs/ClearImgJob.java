package com.lh.jobs;

import com.lh.constant.RedisConstant;
import com.lh.utils.QiniuUtils;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Set;

public class ClearImgJob {
    @Resource
    private JedisPool jedisPool;

    public void clearImg(){
        //获取需要清理的图片
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        //清理图片
        if(set!=null){
            for (String s : set) {
                //在七牛云上删除
                QiniuUtils.deleteFileFromQiniu(s);
                //从redis中删除
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,s);
            }
        }
        //最后将两个set集合删除
        jedisPool.getResource().del(RedisConstant.SETMEAL_PIC_RESOURCES);
        jedisPool.getResource().del(RedisConstant.SETMEAL_PIC_DB_RESOURCES);
    }
}
