package com.xiayule.getll.service;

import redis.clients.jedis.Jedis;

/**
 * Created by tan on 14-7-30.
 * Jedis Pool 接口
 */
public interface RedisDataSource {
    public Jedis getJedisClient();
    public void returnResource(Jedis jedis);
    public void returnResource(Jedis jedis, boolean broken);
}
