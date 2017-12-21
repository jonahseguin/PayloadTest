package com.jonahseguin.payloadtest.database;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

/**
 * Created by jonahseguin on 2017-08-12.
 */
public class RedisManager {

    private final JedisPool jedisPool;
    private final String redisHost = "localhost";
    private final int redisPort = 6379;
    private final boolean useRedisAuth = false;
    private final String redisPassword = "password";

    public RedisManager() {
        this.jedisPool = new JedisPool(new JedisPoolConfig(), redisHost, redisPort);
    }

    public Jedis getJedisResource() {
        try {
            Jedis jedis = jedisPool.getResource();
            if (useRedisAuth) {
                jedis.auth(redisPassword);
            }
            return jedis;
        } catch (JedisException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void returnJedisResource(Jedis jedis) {
        jedis.close();
    }


}
