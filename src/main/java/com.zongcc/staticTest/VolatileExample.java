package com.zongcc.staticTest;

/**
 * Created by chunchengzong on 2017-06-12.
 */
public class VolatileExample {

    long vl = 0L;
    public void set(long l){
        vl = l;
    }
    public void getIncrement(){
        vl++;
    }
    public long get(){
        return vl;
    }
}
