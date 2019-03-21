package com.zongcc.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 基于 httpclient 4.3.1版本的 http工具类
 * Created by binhuang200996 on 2015/8/20.
 */
public class HttpTookit {

    private static final Logger logger = LoggerFactory.getLogger(HttpTookit.class);

    private static final String CHARSET = "UTF-8";
    private static final RequestConfig CONFIG = RequestConfig.custom().setConnectTimeout(30000).setSocketTimeout(15000).build();

    /**
     * HTTP Get 默认使用UTF-8编码
     *
     * @param url    请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @return
     */
    public static String doGet(String url, Map<String, String> params) throws RuntimeException {
        return doGet(url, params, CHARSET);
    }

    /**
     * HTTP Post 默认使用UTF-8编码
     *
     * @param url    请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @return
     */
    public static String doPost(String url, Map<String, String> params) throws RuntimeException {
        return doPost(url, params, CHARSET);
    }

    /**
     * HTTP Get
     *
     * @param url     请求的url地址 ?之前的地址
     * @param params  请求的参数
     * @param charset 编码格式
     * @return 页面内容
     */
    public static String doGet(String url, Map<String, String> params, String charset) throws RuntimeException {

        logger.info("===HttpTookit doGet=== url:{} charset:{} params:{}", new Object[]{url, charset, params});

        if (StringUtils.isBlank(url)) {
            return null;
        }

        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(CONFIG).build();
        CloseableHttpResponse response = null;

        try {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (StringUtils.isNotBlank(value)) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            }
            HttpGet httpGet = new HttpGet(url);
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                httpGet.abort();
                throw new RuntimeException("HttpClient doGet error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, charset);
            }
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
            logger.error("===HttpTookit doGet=== url:{} charset:{} params:{} error:{}", new Object[]{url, charset, params, e});
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * HTTP Post 获取内容
     *
     * @param url     请求的url地址 ?之前的地址
     * @param params  请求的参数
     * @param charset 编码格式
     * @return 页面内容
     */
    public static String doPost(String url, Map<String, String> params, String charset) throws RuntimeException {

        logger.info("===HttpTookit doPost=== url:{} charset:{} params:{}", new Object[]{url, charset, params});

        if (StringUtils.isBlank(url)) {
            return null;
        }

        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(CONFIG).build();
        CloseableHttpResponse response = null;

        try {
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (StringUtils.isNotBlank(value)) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            if (pairs != null && pairs.size() > 0) {
                httpPost.setEntity(new UrlEncodedFormEntity(pairs, charset));
            }
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                httpPost.abort();
                throw new RuntimeException("HttpClient doPost error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, charset);
            }
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
            logger.error("===HttpTookit doPost=== url:{} charset:{} params:{} error:{}", new Object[]{url, charset, params, e});
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String getData = doGet("http://sohu-sales-approval-test.apps.sohuno.com/login.do", null);
        System.out.println(getData);
        System.out.println("----------------------分割线-----------------------");
        String postData = doPost("http://sohu-sales-approval-test.apps.sohuno.com/login.do", null);
        System.out.println(postData);
    }
}