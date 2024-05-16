package com.springboot.intuit.service;

public interface RedisService {

    public void setValueWithTtl(String key, Object value, long ttlSeconds) ;

    public Object getValue(String key) ;

    public void deleteKey(String key) ;
}
