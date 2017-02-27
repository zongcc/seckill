/*
package com.zongcc.service;

import com.zongcc.cache.memcache.MemcachedClient;
import com.zongcc.dto.Exposer;
import com.zongcc.dto.SeckillExecution;
import com.zongcc.exception.RepeatKillException;
import com.zongcc.exception.SeckillCloseException;
import com.zongcc.exception.SeckillException;
import com.zongcc.model.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

*/
/**
 * Created by chunchengzong on 2016-09-20.
 *//*

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext-*.xml"})
public class SeckillServiceTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    SeckillService seckillService;
    @Resource
    MemcachedClient memcachedClient;

    @Test
    public void testGetSeckillList() throws Exception {
        logger.info("memcached-----get-------------",(List<Seckill>)memcachedClient.get("testGetSeckillList"));
        List<Seckill> list = seckillService.getSeckillList(0, 10);
        memcachedClient.set("testGetSeckillList",list);
        logger.info("testGetSeckillList=== list{}",list);
    }

    @Test
    public void testGetById() throws Exception {
        Seckill seckill = seckillService.getById(1l);
        logger.info("seckill{}",seckill);
    }

    @Test
    public void testExportSeckillUrl() throws Exception {
        Long seckillId = 2l;
        Long userPhone = 18210300633L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if(exposer.getExposed()){
            try {
                SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, exposer.getMd5());
            }catch (SeckillCloseException se){
                logger.error("秒杀已结束"+se.getMessage());
            }catch (RepeatKillException se){
                logger.error("重复秒杀"+se.getMessage());
            }catch (SeckillException se){
                logger.error("系统错误"+se.getMessage());
            }
        }else {
            logger.info("秒杀未开始或者已经无库存");
        }
    }

    @Test
    public void executeSeckillProcedureTest(){
        long seckillId = 1;
        long phone = 18210300633l;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if(exposer.getExposed()){
            String md5 = exposer.getMd5();
            SeckillExecution seckillExecution = seckillService.executeSeckillProcedure(seckillId, phone, md5);
            logger.info("seckillExecution:"+seckillExecution.getStateInfo());
        }
    }

}*/
