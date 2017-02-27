package com.zongcc.service.impl;

import com.zongcc.cache.redis.RedisClient;
import com.zongcc.dao.SeckillDao;
import com.zongcc.dao.SuccessKilledDao;
import com.zongcc.dto.Exposer;
import com.zongcc.dto.SeckillExecution;
import com.zongcc.enums.SeckillStateEnum;
import com.zongcc.exception.RepeatKillException;
import com.zongcc.exception.SeckillCloseException;
import com.zongcc.exception.SeckillException;
import com.zongcc.model.Seckill;
import com.zongcc.model.SuccessKilled;
import com.zongcc.service.SeckillService;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chunchengzong on 2016-09-20.
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String slat = "zongcc%%@&md8&&#KD93";

    @Resource
    SeckillDao seckillDao;
    @Resource
    SuccessKilledDao successKilledDao;
    /*@Resource
    RedisClient redisClient;*/

    @Override
    public List<Seckill> getSeckillList(int offset, int limit) {
        return seckillDao.queryAll(offset, limit);
    }

    @Override
    //@CacheStore(key = "'getById'+#seckillId")
    public Seckill getById(Long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(Long seckillId) {
        Seckill seckill = null;
        /*String key = "seckillUrl" + seckillId;
        byte[] bytes = redisClient.get(key.getBytes());
        try {
            if (null != bytes) {
                seckill = (Seckill) redisClient.deserializeObj(bytes);
            } else {
                seckill = seckillDao.queryById(seckillId);
                byte[] result = redisClient.serializeObj(seckill);
                redisClient.setAndExpire(key.getBytes(), result, 60 * 60);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }*/

        seckill = seckillDao.queryById(seckillId);
        if (seckill == null) {
            return new Exposer(false, seckillId);
        }
        //判断秒杀时间是否有效范围内
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date currentTime = new Date();
        if (startTime.getTime() > currentTime.getTime() || endTime.getTime() < currentTime.getTime()) {
            return new Exposer(false, seckillId, startTime, endTime, currentTime);
        }
        //加密,执行秒杀作为判断
        String md5 = getMd5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    /**
     * 加密seckillId和slat生成md5
     *
     * @param seckillId
     * @return
     */
    private String getMd5(Long seckillId) {
        String base = seckillId + "/" + slat;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

    @Override
    @Transactional
    public SeckillExecution executeSeckill(Long seckillId, Long userPhone, String md5) throws SeckillCloseException, RepeatKillException, SeckillException {
        //如果传回来的md5值为空或者不同，则说明用户对数据进行了重写
        if (null == md5 || !md5.equals(getMd5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }
        //执行秒杀：减库存+记录购买行为
        Date currentTime = new Date();
        try {
            int num = seckillDao.reduceNumber(seckillId, currentTime);
            if (num <= 0) {
                //没有更新操作(库存没有或者时间不对)
                throw new SeckillCloseException("seckill is close");
            } else {
                //记录购买行为
                int result = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                if (result <= 0) {
                    throw new RepeatKillException("repeat seckill");
                } else {
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SECKILL_SUCCSS, successKilled);
                }
            }
        } catch (RepeatKillException re) {
            throw re;
        } catch (SeckillCloseException ce) {
            throw ce;
        } catch (Exception se) {
            throw new SeckillException("seckill inner error:" + se.getMessage());
        }

    }

    @Override
    public SeckillExecution executeSeckillProcedure(Long seckillId, Long userPhone, String md5) {
        if (null == md5 || !md5.equals(getMd5(seckillId))) {
            return new SeckillExecution(seckillId, SeckillStateEnum.SECKILL_DATA);
        }
        Date killTime = new Date();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("seckillId", seckillId);
        params.put("phone", userPhone);
        params.put("killTime", killTime);
        params.put("result", null);
        try {
            seckillDao.killByProcedure(params);
            int result = MapUtils.getInteger(params, "result", -2);
            if (result == 1) {
                SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                return new SeckillExecution(seckillId, SeckillStateEnum.SECKILL_SUCCSS, successKilled);
            } else {
                return new SeckillExecution(seckillId, SeckillStateEnum.getCode(result));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new SeckillExecution(seckillId, SeckillStateEnum.SECKILL_ERROR);
        }
    }
}
