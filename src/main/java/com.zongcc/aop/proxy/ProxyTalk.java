package com.zongcc.aop.proxy;

/**
 * Created by zongcc on 2016/12/26.
 */
public class ProxyTalk implements ITalk {
    private ITalk iTalk;
    public ProxyTalk(ITalk talk){
        this.iTalk = talk;
    }
    @Override
    public void talk(String message) {
        iTalk.talk(message);
    }
}
