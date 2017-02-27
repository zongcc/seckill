package com.zongcc.dao;

import com.zongcc.model.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 * Created by chunchengzong on 2016-09-20.
 */
public interface SuccessKilledDao {
    /**
     * 插入购买明细，可过滤重复
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccessKilled(@Param("seckillId")Long seckillId, @Param("userPhone")Long userPhone);

    /**
     * 根据id查询秒杀结果以及携带着秒杀对象
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId")Long seckillId,@Param("userPhone")Long userPhone);
}
