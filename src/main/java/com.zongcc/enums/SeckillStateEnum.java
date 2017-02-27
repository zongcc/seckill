package com.zongcc.enums;

/**
 * Created by chunchengzong on 2016-09-20.
 */
public enum  SeckillStateEnum {

    SECKILL_SUCCSS(1,"秒杀成功"),
    SECKILL_END(0,"秒杀结束"),
    SECKILL_REPEAT(-1,"重复秒杀"),
    SECKILL_ERROR(-2,"系统异常"),
    SECKILL_DATA(-3,"数据篡改");

    private int state;

    private String stateInfo;

     SeckillStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public static SeckillStateEnum getCode(int index){
        for(SeckillStateEnum state : values()){
            if(state.getState() == index){
                return state;
            }
        }
        return null;
    }
}
