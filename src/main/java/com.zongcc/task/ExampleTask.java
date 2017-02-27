package com.zongcc.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by chunchengzong on 2016-11-09.
 */
@Component
public class ExampleTask {
    private final static Logger logger = LoggerFactory.getLogger(ExampleTask.class);

    @Scheduled(cron = "* * * 1 * ?")
    public void testTask(){
        System.out.println("定时任务:"+ Thread.currentThread().getName()+":"+ new Date().getTime());
    }
}
