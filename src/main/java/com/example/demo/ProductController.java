package com.example.demo;

import com.example.demo.config.RedisConfig;
import com.example.demo.config.RedisPoolFactory;
import com.example.demo.mapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;

@EnableCaching
@Slf4j
@SpringBootTest
@RestController
@RequestMapping("/product")
public class ProductController {

    /*@Autowired
    private JedisPool jedisPool;
    @Autowired
    private JedisPoolConfig jedisPoolConfig;*/

/*
    @Bean
    @ConfigurationProperties("redis")
    public JedisPoolConfig jedisPoolConfig() {
        return new JedisPoolConfig();
    }

    @Bean(destroyMethod = "close")
    public JedisPool jedisPool(@Value("${spring:redis:host}") String host) {
        return new JedisPool(jedisPoolConfig(), "localhost");
    }*/


    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private RedisPoolFactory redisPoolFactory;
    @Autowired
    private RedisConfig redisConfig;


    @GetMapping("/get/{id}")
    public Product getProductInfo2(
            @PathVariable("id")
                    Long id) {
        log.info("############################");
        log.info(redisConfig.toString());

        try (Jedis jedis = redisPoolFactory.JedisPoolFactory().getResource()) {
            productMapper.findAll().forEach(c -> {
                jedis.hset("products",
                        c.getName(),
                        Long.toString(c.getPrice()));
            });

            log.info("22222222222222222222");

            Map<String, String> list = jedis.hgetAll("products");
            log.info("Menu: - {}", list);

        }
        return productMapper.select(id);
    }

    @GetMapping("/get3/{id}")
    public Product getProductInfo3(
            @PathVariable("id")
                    Long id) {

        log.info(redisConfig.toString());

        try (Jedis jedis = redisPoolFactory.JedisPoolFactory().getResource()) {

                jedis.hset("products", "123", "456");
                jedis.hgetAll("products");

        }
        return productMapper.select(id);
    }

    @Cacheable(cacheNames="hello", key = "#id")
    @GetMapping("get4/{id}")
    public Product getProductInfo4(
            @PathVariable("id")
                    Long id) {
        return productMapper.select(id);
    }

    @Cacheable(cacheNames="world", key = "#id")
    @GetMapping("get5/{id}")
    public long getProductInfo5(
            @PathVariable("id")
                    Long id) {
        return productMapper.getPrice(id);
    }

    //@CachePut(value="product_put")
    @PutMapping("/{id}")
    public Product updateProductInfo(
            @PathVariable("id")
                    Long productId,
            @RequestBody
                    Product newProduct) {
        Product product = productMapper.select(productId);
        if (product == null) {
            throw new ProductNotFoundException(productId);
        }
        product.setName(newProduct.getName());
        product.setPrice(newProduct.getPrice());
        productMapper.update(product);
        return product;

    }

}