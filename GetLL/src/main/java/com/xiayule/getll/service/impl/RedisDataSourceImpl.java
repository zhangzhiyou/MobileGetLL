package com.xiayule.getll.service.impl;

import com.xiayule.getll.service.RedisDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by tan on 14-7-30.
 */
//@Component
public class RedisDataSourceImpl implements RedisDataSource {

//    @Autowired
    private JedisPool jedisPool;

    @Override
    public Jedis getJedisClient() {
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }

    @Override
    public void returnResource(Jedis jedis) {
        jedisPool.returnResource(jedis);
    }

    @Override
    public void returnResource(Jedis jedis, boolean broken) {
        if (broken) {
            jedisPool.returnBrokenResource(jedis);
        } else {
            jedisPool.returnResource(jedis);
        }
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
