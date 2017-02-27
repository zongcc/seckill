package com.zongcc.dto;

import java.util.Date;

/**暴露秒杀地址DTO
 * Created by chunchengzong on 2016-09-20.
 */
public class Exposer {

    private Boolean exposed;

    private String md5;

    private Long seckillId;

    private Date start;

    private Date end;

    //当前系统时间（毫秒）
    private  Date now;

    public Exposer(Boolean exposed, Long seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }

    public Exposer(Boolean exposed, String md5, Long seckillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public Exposer(Boolean exposed, Long seckillId, Date start, Date end, Date now) {
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.start = start;
        this.end = end;
        this.now = now;
    }

    public Boolean getExposed() {
        return exposed;
    }

    public void setExposed(Boolean exposed) {
        this.exposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(Long seckillId) {
        this.seckillId = seckillId;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
    }
}
