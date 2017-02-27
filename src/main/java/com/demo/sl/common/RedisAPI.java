package com.demo.sl.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 *基于jedispool创建的
 */
public class RedisAPI {
    public JedisPool jedisPool;//redis的连接池对象

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * 给redis设置key跟value
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key,String value){
        Jedis jedis=null;
        try {
            //从连接池中获得一个jedis对象
            jedis = jedisPool.getResource();
            jedis.set(key,value);
            return true;
        }catch (Exception e){
            e.printStackTrace();

        }finally {
            //数据返还回连接池
            returnResource(jedisPool,jedis);
        }
        return false;
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public boolean exist(String key){
        Jedis jedis=null;
        try {
            jedis = jedisPool.getResource();
            Boolean exists = jedis.exists(key);
            return exists;
        }catch (Exception e){
            e.printStackTrace();//打印异常信息和原因相关
        }finally {
            //数据返还回连接池
            returnResource(jedisPool,jedis);
        }
        return false;
    }
    public static void returnResource(JedisPool jedisPool,Jedis jedis){
        if (jedis!=null){
            jedisPool.returnResource(jedis);
        }

    }
    /**
     * 获取数据
     * @param key
     * @return
     */
    public String get(String key){
        Jedis jedis=null;
        try{
            jedis = jedisPool.getResource();//从连接池中得到一个jedis对象
            String value = jedis.get(key);
           return value;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //数据返还回连接池
            returnResource(jedisPool,jedis);
        }
       return null;

    }
}
