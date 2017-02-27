package com.zongcc.dao;

import com.zongcc.model.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by chunchengzong on 2016-09-20.
 */
public interface SeckillDao {
    /**
     * 减库存操作
     * @param seckillId
     * @param killTime
     * @return
     */
    int reduceNumber(@Param("seckillId")Long seckillId, @Param("killTime")Date killTime);

    /**
     * 根据id查询秒杀对象
     * @param seckillId
     * @return
     */
    Seckill queryById(Long seckillId);

    /**
     * 查询秒杀商品列表
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit")int limit);

    /**
     * 秒杀通过存储过程
     * @param params
     */
    void killByProcedure(Map<String,Object> params);
}
