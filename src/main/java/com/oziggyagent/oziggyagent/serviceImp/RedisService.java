package com.oziggyagent.oziggyagent.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public void SetValue(String key,String value){
        redisTemplate.opsForValue().set(key, value);
    }
    public String getValue(String key){
        return redisTemplate.opsForValue().get(key);

    }

}

