package com.zongcc.cache.redis;

import com.alibaba.com.caucho.hessian.io.Hessian2Input;
import com.alibaba.com.caucho.hessian.io.Hessian2Output;
import com.alibaba.com.caucho.hessian.io.SerializerFactory;
import com.zongcc.utils.ProtostuffUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

public class RedisClient {

    public static final String NOT_FOUND = "nil";
    private static final SerializerFactory SERIALIZERFACTORY = new SerializerFactory();
    private volatile JedisPool jedisPool = null;
    private JedisPoolConfig jedisPoolConfig;
    private String host;
    private Integer port;
    private String password;
    private Object lock = new Object();

    private int resetNum = 3;

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
        this.jedisPoolConfig = jedisPoolConfig;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Jedis getJedis() {
        Jedis jedis = null;
        int tryCount = 0;
        while (jedis == null && tryCount < resetNum) {
            try {
                if (checkConnectionPool() != null) {
                    jedis = jedisPool.getResource();
                }
            } catch (Exception e) {
                jedisPool = null;
                e.printStackTrace();
            }
        }
        return jedis;
    }

    public JedisPool checkConnectionPool() {
        if (jedisPool == null) {
            synchronized (lock) {
                if (jedisPool == null) {
                    try {
                        //jedisPool = new JedisPool(jedisPoolConfig, host, port);
                        jedisPool = new JedisPool(jedisPoolConfig, host, port, 5000, password);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return jedisPool;
    }

    public void releaseJedisInstance(Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }

    public Boolean exists(String key) {
        Boolean result = Boolean.FALSE;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseJedisInstance(jedis);
        }
        return result;
    }

    public Long hdel(String key, String field) {
        Long result = 0L;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.hdel(key, field);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseJedisInstance(jedis);
        }
        return result;
    }

    public List<String> keys(String pattern) {
        List<String> result = new ArrayList<String>();
        Set<String> set = new HashSet<String>();
        Jedis jedis = null;
        try {
            jedis = getJedis();
            set = jedis.keys(pattern);
            if (set != null && !set.isEmpty()) {
                final Iterator<String> ite = set.iterator();
                while (ite.hasNext()) {
                    result.add((String) ite.next());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseJedisInstance(jedis);
        }
        return result;
    }

    public String get(String key) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String str = jedis.get(key);
            if (!NOT_FOUND.equals(str)) {
                result = str;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseJedisInstance(jedis);
        }
        return result;
    }

    public String set(String key, String value) {
        String result = "";
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseJedisInstance(jedis);
        }
        return result;
    }

    public Long expire(String key, int seconds) {
        Long result = 0L;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.expire(key, seconds);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseJedisInstance(jedis);
        }
        return result;
    }

    public void setAndExpire(String key, String value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key, value);
            jedis.expire(key, seconds);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseJedisInstance(jedis);
        }
    }

    public void set(byte[] key, byte[] o) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key, o);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseJedisInstance(jedis);
        }
    }

    public byte[] get(byte[] key) {
        Jedis jedis = null;
        byte[] o = null;
        try {
            jedis = getJedis();
            o = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseJedisInstance(jedis);
        }
        return o;
    }

    public void setAndExpire(byte[] key, byte[] s, int expire) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key, s);
            jedis.expire(key, expire);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseJedisInstance(jedis);
        }
    }

    public byte[] serializeObj(Object obj) throws IOException {
        if (obj == null) throw new NullPointerException();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Hessian2Output out = new Hessian2Output(os);
        out.setSerializerFactory(SERIALIZERFACTORY);
        out.writeObject(obj);
        out.flush();
        return os.toByteArray();
    }

    public byte[] serializeObjProtostuff(Object obj, Class clazz) throws IOException {
        if (obj == null) throw new NullPointerException();
        return ProtostuffUtil.serializeProtoStuff(obj, clazz);
    }

    public Object deserializeObj(byte[] by) throws IOException {
        if (by == null) throw new NullPointerException();
        ByteArrayInputStream is = new ByteArrayInputStream(by);
        Hessian2Input hi = new Hessian2Input(is);
        hi.setSerializerFactory(SERIALIZERFACTORY);
        return hi.readObject();
    }

    public Object deserializeObjProtostuff(byte[] by, Class clazz) throws IOException {
        if (by == null) throw new NullPointerException();
        return ProtostuffUtil.deSerializeProtoStuff(by, clazz);
    }

    public Long del(String... keys) {
        Long result = 0L;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.del(keys);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseJedisInstance(jedis);
        }
        return result;
    }

    public Long del(byte[] key) {
        Long result = 0L;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseJedisInstance(jedis);
        }
        return result;
    }

    public Long rpush(String key, String string) {
        Long result = 0L;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.rpush(key, string);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseJedisInstance(jedis);
        }
        return result;
    }

    public long setnexAndExpire(String key, String value, int expire) {
        Jedis jedis = null;
        long result = 0;
        try {
            jedis = getJedis();
            result = jedis.setnx(key, value);
            if (result == 1) {
                jedis.expire(key, expire);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseJedisInstance(jedis);
        }
        return result;
    }

    /**
     * 获取缓存锁
     *
     * @param key
     * @param expire 有效单位：秒
     * @return true:获取到了锁，false:未能获取到锁
     */
    public boolean getCacheLock(String key, int expire) {
        return setnexAndExpire(key + ":lock", "object", expire) == 1 ? true : false;
    }

    /**
     * 删除缓存锁
     *
     * @param key
     */
    public void delCacheLock(String key) {
        del(key + ":lock");
    }
}