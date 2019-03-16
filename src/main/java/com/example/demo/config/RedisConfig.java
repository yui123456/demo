package com.example.demo.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:application.yml")
public class RedisConfig extends CachingConfigurerSupport {

//extends redis.clients.jedis.JedisPoolConfig

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;//秒
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.jedis.pool.max-active}")
    private int poolMaxTotal;
    @Value("${spring.redis.jedis.pool.max-idle}")
    private int poolMaxIdle;
    @Value("${spring.redis.jedis.pool.min-idle}")
    private int poolMinIdle;
    @Value("${spring.redis.jedis.pool.max-wait}")
    private int poolMaxWait;//秒




}


