package com.webill.core.service;


import com.webill.core.model.RedisKeyDto;

public interface RedisService{
	
    void addData(RedisKeyDto redisKeyDto);
    
    void delete(RedisKeyDto redisKeyDto);
    
    RedisKeyDto redisGet(RedisKeyDto redisKeyDto);
    
    void addRedisData(RedisKeyDto redisKeyDto,int outTime);
}