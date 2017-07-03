package com.zongcc.thread.chapter04;

/**
 * Created by chunchengzong on 2017-06-27.
 */
public class SmsSendAsync {
    private String prtNo;
    public SmsSendAsync(String prtNo) {
        this.prtNo = prtNo;
    }

    public void send() {
        //新定义线程来处理发送
        new Thread(){
            public void run(){
                //这里可以写一个等待方法，让当前线程等待10秒钟现象更明显
                SleepUtils.second(10);
                System.out.println("发送短信......"+prtNo);
            }
        }.start();
    }

    public static void main(String[] args) {
        String prtNo = "1001200912310155555";
        //调用异步发送短信
        SmsSendAsync sendAsync = new SmsSendAsync(prtNo);
        sendAsync.send();
        System.out.println("已经签单");

    }
}
