/*
package com.zongcc.dao;

import com.zongcc.model.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

*/
/**
 * Created by chunchengzong on 2016-09-20.
 *//*

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext-*.xml"})
public class SuccessKilledDaoTest {

    private Logger logger = LoggerFactory.getLogger(SuccessKilledDaoTest.class);

    @Resource
    private  SuccessKilledDao successKilledDao;

    @Test
    public void testSuccessKilledLogic() throws Exception {
        Long seckillId = 1L;
        Long userPhone = 18600839035L;
        int r = successKilledDao.insertSuccessKilled(seckillId,userPhone);
        logger.info("SuccessKilledDaoTest====insertSuccessKilled== result:{}",new Object[]{r});
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
        logger.info("SuccessKilledDaoTest====queryByIdWithSeckill== seckillId:{},name:{},state:{}",
                new Object[]{successKilled.getSeckillId(),successKilled.getSeckill().getName(),successKilled.getState()});    }
}*/
