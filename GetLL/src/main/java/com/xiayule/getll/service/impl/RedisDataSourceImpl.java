package com.xiayule.getll.service.impl;

import com.xiayule.getll.service.RedisDataSource;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by tan on 14-7-30.
 */
public class RedisDataSourceImpl implements RedisDataSource {
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
