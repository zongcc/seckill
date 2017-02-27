package com.zongcc.service;

import com.zongcc.dto.Exposer;
import com.zongcc.dto.SeckillExecution;
import com.zongcc.exception.RepeatKillException;
import com.zongcc.exception.SeckillCloseException;
import com.zongcc.exception.SeckillException;
import com.zongcc.model.Seckill;

import java.io.IOException;
import java.util.List;

/**
 * Created by chunchengzong on 2016-09-20.
 */
public interface SeckillService {
    /**
     * 获取所有秒杀记录
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> getSeckillList(int offset,int limit);

    /**
     * 获取当个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getById(Long seckillId);

    /**
     * 暴露秒杀接口，否则输出系统时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(Long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(Long seckillId, Long userPhone, String md5)
            throws SeckillCloseException,RepeatKillException,SeckillException;
    /**
     * 执行秒杀操作by存储过程
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckillProcedure(Long seckillId, Long userPhone, String md5);
}
