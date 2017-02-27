/*
package com.zongcc.dao;

import com.zongcc.model.Seckill;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

*/
/**
 * Created by chunchengzong on 2016-09-20.
 *//*

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext-*.xml"})
public class SeckillDaoTest {

    private static Logger log = LoggerFactory.getLogger(SeckillDaoTest.class);

    @Resource
     private SeckillDao seckillDao;

    @After
    public void tearDown() throws Exception {
        log.info("SeckillDaoTest====tearDown=========");
    }

    @Test
    public void testReduceNumber() throws Exception {
        Long id = 1L;
       int r = seckillDao.reduceNumber(id,new Date());
        System.out.println(r);
    }

    @Test
    public void testQueryById() throws Exception {
        Long id = 1L;
        Seckill seckill =  seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void testQueryAll() throws Exception {
        List<Seckill> list =  seckillDao.queryAll(0,10);
        for (Seckill seckill:list){
           // System.out.println(seckill);
            log.info("seckill======> seckillId:{},name:{},number:{}",
                    new Object[]{seckill.getSeckillId(),seckill.getName(),seckill.getNumber()});
        }
    }
}*/
