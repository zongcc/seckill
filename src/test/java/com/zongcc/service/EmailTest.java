/*
package com.zongcc.service;

import com.zongcc.mail.EmailUtil;
import junit.framework.TestCase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

*/
/**
 * Created by chunchengzong on 2016-09-20.
 *//*

public class EmailTest extends TestCase{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Test
    public void testService(){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-mail.xml");
        EmailUtil mail = (EmailUtil)context.getBean("simpleMail");
        mail.sendMail("Spring SMTP Mail Subject", "Spring SMTP Mail Text", "dxrczcc@163.com");
        System.out.println("--------------------我是无聊的分割线-------------------------------------");
        mail.sendMimeMessage();
    }

}*/
