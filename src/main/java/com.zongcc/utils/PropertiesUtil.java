package com.zongcc.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Properties工具类
 * Created by jianyang206881 on 2015/11/23.
 */
public class PropertiesUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private static PropertiesUtil INSTANCE;
    private static Properties PROPERTIES;

    private PropertiesUtil(String propertiesPath) {
        try {
            logger.info("===PropertiesUtil=== load properties in path:{}", propertiesPath);
            InputStream is = new ClassPathResource(propertiesPath).getInputStream();
            PROPERTIES = new Properties();
            PROPERTIES.load(is);
        } catch (IOException e) {
            logger.error("===PropertiesUtil===> propertiesPath:{} error:{},", new Object[]{propertiesPath, e});
        }
    }

    public static synchronized PropertiesUtil getInstance(String propertiesPath) {
        if (INSTANCE == null) {
            INSTANCE = new PropertiesUtil(propertiesPath);
        }
        return INSTANCE;
    }

    /**
     * 返回Properties
     *
     * @return
     */
    public Properties getProperties() {
        return PROPERTIES;
    }

    /**
     * 获取value值
     *
     * @param key
     * @return
     */
    public String getValue(String key) {
        return PROPERTIES.get(key).toString();
    }

}
