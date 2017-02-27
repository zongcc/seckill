package com.zongcc.cache.memcache;

import net.rubyeye.xmemcached.*;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class MemcachedClient implements CacheClient {
    private static Logger logger = LoggerFactory.getLogger(MemcachedClient.class);

    public static final int DEFAULT_CACHE_TIME = 60*60; //一个小时
    public static final int CONNECTION_POOL_SIZE =30;

    private net.rubyeye.xmemcached.MemcachedClient memcachedClient;
    private String host;
    private Integer port;
    private String prefixKey;

    private void init() throws IOException {
       /* MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddressMap(getServerStr()));
        builder.setSessionLocator(new KetamaMemcachedSessionLocator());
        builder.setCommandFactory(new BinaryCommandFactory());
        builder.setConnectionPoolSize(CONNECTION_POOL_SIZE);
        //cacheAuthoInfo(builder);*/
        MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(getServerStr()));
        builder.setSessionLocator(new KetamaMemcachedSessionLocator());
        builder.setCommandFactory(new BinaryCommandFactory());
        builder.setConnectionPoolSize(CONNECTION_POOL_SIZE);
        // 宕机报警
        builder.setFailureMode(true);
        this.memcachedClient = builder.build();
        logger.info("++++++++memcached...........init...........success.....");
    }

    private void restCahceClient() throws IOException {
        this.memcachedClient = null;
        /*MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddressMap(getServerStr()));
        builder.setSessionLocator(new KetamaMemcachedSessionLocator());
        builder.setCommandFactory(new BinaryCommandFactory());
        builder.setConnectionPoolSize(CONNECTION_POOL_SIZE);
        //cacheAuthoInfo(builder);
        this.memcachedClient = builder.build();*/
        MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(getServerStr()));
        builder.setSessionLocator(new KetamaMemcachedSessionLocator());
        builder.setCommandFactory(new BinaryCommandFactory());
        builder.setConnectionPoolSize(CONNECTION_POOL_SIZE);
        // 宕机报警
        builder.setFailureMode(true);
        this.memcachedClient = builder.build();
        logger.info("++++++++memcached...........reStart...........success.....");
    }

    public String getServerStr() throws IOException {
        StringBuffer serverStr = new StringBuffer();
        serverStr.append(host).append(":").append(port).append(" ");
        return serverStr.toString();
    }

    //SASL验证
    private void cacheAuthoInfo(MemcachedClientBuilder builder) throws IOException {
        try {
            //builder.addAuthInfo(AddrUtil.getOneAddress(host + ":" + port), AuthInfo.plain(uid, password));
        }catch (Exception e){
            logger.error("SASL验证报错："+e.getMessage());
        }

    }

    public <T> Map<InetSocketAddress, Map<String, String>> getAllKeys() {
        Map<InetSocketAddress, Map<String, String>> allKeys = null;
        try {
            //allKeys = memcachedClient.getStats();
            allKeys = memcachedClient.getStatsByItem("MemcachedClient-0");
            KeyIterator it = memcachedClient.getKeyIterator(AddrUtil.getOneAddress(getServerStr()));
            while (it.hasNext()) {
                String key = it.next();
            }
        } catch (MemcachedException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allKeys;
    }

    public <T> T get(String key) {
        try {
            key = prefixKey + "." + key;
            logger.info("++++++++memcached get key=" + key);
            return (T) memcachedClient.get(key, 3000);
        } catch (Exception e) {
            logger.error(" key=" + key);
            e.printStackTrace();
        }
        return null;
    }


    public <T> Map<String, Object> getMulti(String[] keys) {
        try {
            List<String> list = new ArrayList<String>();
            Collections.addAll(list, keys);
            return memcachedClient.get(list);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }


    public boolean set(String key, Object value) {
        try {
            key = prefixKey + "." + key;
            logger.info("++++++++memcached set key=" + key);
            //默认设置缓存时间为一个小时(这里失效时间单位为秒)
            return memcachedClient.add(key, DEFAULT_CACHE_TIME, value, 3000);
        } catch (Exception e) {
            logger.error("key = " + key);
            e.printStackTrace();
        }
        return false;
    }


    public boolean set(String key, Object value, int seconds) {
        try {
            key = prefixKey + "." + key;
            logger.info("++++++++memcached set key=" + key);
            return memcachedClient.add(key, seconds, value, 3000);
        } catch (Exception e) {
            logger.error("key = " + key);
            e.printStackTrace();
        }

        return false;
    }


    public boolean delete(String key) {
        try {
            key = prefixKey + "." + key;
            logger.info("++++++++memcached delete key=" + key);
            return memcachedClient.delete(key);
        } catch (Exception e) {
            logger.error("key = " + key);
            e.printStackTrace();
        }
        return false;
    }


    public long incr(String key, long value) {
        try {
            key = prefixKey + "." + key;
            logger.info("++++++++memcached incr key=" + key + ",value=" + value);
            return memcachedClient.incr(key, value);
        } catch (Exception e) {
            logger.error("key = " + key);
            e.printStackTrace();
        }

        return 0;
    }


    public long decr(String key, long value) {
        try {
            key = prefixKey + "." + key;
            logger.info("++++++++memcached decr key=" + key + ",value=" + value);
            return memcachedClient.decr(key, value);
        } catch (Exception e) {
            logger.error("key = " + key);
            e.printStackTrace();
        }
        return 0;
    }


    public long getCounter(String key) {
        try {
            key = prefixKey + "." + key;
            Counter count = memcachedClient.getCounter(key);
            return count.get();
        } catch (Exception e) {
            logger.error("key = " + key);
            e.printStackTrace();
        }
        return 0;
    }


    public boolean storeCounter(String key, long value) {
        return false;
    }


    public void flush() {
        try {
            memcachedClient.flushAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void shutdown(){
        if(null != memcachedClient && !memcachedClient.isShutdown()){
            try {
                memcachedClient.shutdown();
                logger.info("++++++++++++++mecached.......shutdown.....................");
            } catch (IOException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }


    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public Integer getPort() {
        return port;
    }
    public void setPort(Integer port) {
        this.port = port;
    }
    public void setPrefixKey(String prefixKey) {
        this.prefixKey = prefixKey;
    }


}
