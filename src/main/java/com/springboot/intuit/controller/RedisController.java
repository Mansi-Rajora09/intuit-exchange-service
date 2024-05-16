package com.springboot.intuit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.intuit.service.RedisService;

@RestController
public class RedisController {

    @Autowired
    private RedisService redisService;

    @Value("${spring.redis.default-ttl}")
    private int redisKeyTtl;

    @PostMapping("/set/{key}/{value}")
    public String setValue(@PathVariable String key, @PathVariable String value) {
        redisService.setValueWithTtl(key, value, redisKeyTtl);
        return "Value set successfully";
    }

    @GetMapping("/get/{key}")
    public String getValue(@PathVariable String key) {
        return (String) redisService.getValue(key);

    }

}